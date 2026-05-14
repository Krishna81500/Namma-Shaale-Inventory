package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Asset
import com.example.myapplication.data.repository.AssetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import com.example.myapplication.data.remote.GeminiReportGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AssetViewModel(private val repository: AssetRepository) : ViewModel() {

    private val reportGenerator = GeminiReportGenerator(apiKey = "YOUR_GEMINI_API_KEY")

    private val _auditReport = MutableStateFlow<String?>(null)
    val auditReport = _auditReport.asStateFlow()

    private val _isGeneratingReport = MutableStateFlow(false)
    val isGeneratingReport = _isGeneratingReport.asStateFlow()

    val allAssets: StateFlow<List<Asset>> = repository.getAllAssets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun generateReport() {
        viewModelScope.launch {
            _isGeneratingReport.value = true
            val assets = allAssets.value
            _auditReport.value = reportGenerator.generateAuditReport(assets)
            _isGeneratingReport.value = false
        }
    }

    fun clearReport() {
        _auditReport.value = null
    }

    val assetCount: StateFlow<Int> = repository.getAssetCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val pendingIssueCount: StateFlow<Int> = repository.getPendingIssueCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Member Management
    val allMembers: StateFlow<List<com.example.myapplication.data.model.Member>> = repository.getAllMembers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addMember(member: com.example.myapplication.data.model.Member) {
        viewModelScope.launch { repository.insertMember(member) }
    }

    fun updateMember(member: com.example.myapplication.data.model.Member) {
        viewModelScope.launch { repository.updateMember(member) }
    }

    fun getAttendanceForMember(memberId: Long) = repository.getAttendanceForMember(memberId)

    fun logAttendance(attendance: com.example.myapplication.data.model.Attendance) {
        viewModelScope.launch { repository.insertAttendance(attendance) }
    }

    // Authentication Logic
    fun registerUser(user: com.example.myapplication.data.model.User, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                // Check if user exists by email or unique ID
                if (repository.getUserByEmail(user.email) != null) {
                    onResult(false, "Email already registered")
                    return@launch
                }
                if (repository.getUserByUniqueId(user.uniqueId) != null) {
                    onResult(false, "Unique ID already taken")
                    return@launch
                }
                repository.registerUser(user)
                onResult(true, "Registration successful")
            } catch (e: Exception) {
                onResult(false, "Error: ${e.message}")
            }
        }
    }

    fun loginUser(identity: String, passwordHash: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            // Try to find by email first, then by Unique ID
            val userByEmail = repository.getUserByEmail(identity)
            val userByUniqueId = if (userByEmail == null) repository.getUserByUniqueId(identity) else null
            
            val user = userByEmail ?: userByUniqueId
            
            if (user != null && user.passwordHash == passwordHash) {
                onResult(true, "Welcome back, ${user.name}!")
            } else {
                onResult(false, "Invalid credentials")
            }
        }
    }

    fun addAsset(asset: Asset) {
        viewModelScope.launch {
            repository.insertAsset(asset)
        }
    }

    fun seedSampleData() {
        viewModelScope.launch {
            val samples = listOf(
                Asset(name = "Dell Desktop PC", category = "Electronics", location = "Computer Lab", quantity = 15, purchaseDate = "2024-06-10", conditionStatus = "GREEN"),
                Asset(name = "Wooden Teacher Desks", category = "Furniture", location = "Staff Room", quantity = 5, purchaseDate = "2023-11-20", conditionStatus = "YELLOW"),
                Asset(name = "Compound Microscope", category = "Lab", location = "Science Lab", quantity = 8, purchaseDate = "2025-01-15", conditionStatus = "GREEN"),
                Asset(name = "Science NCERT Textbooks", category = "Books", location = "Library", quantity = 120, purchaseDate = "2025-03-01", conditionStatus = "GREEN"),
                Asset(name = "Cricket Kit", category = "Sports", location = "Sports Room", quantity = 2, purchaseDate = "2024-09-05", conditionStatus = "RED")
            )
            samples.forEach { repository.insertAsset(it) }

            val memberSamples = listOf(
                com.example.myapplication.data.model.Member(name = "Rohit Singh", role = "Admin", status = "Present", bio = "Android Developer & School Lead", responsibilities = "IT Support, Inventory Management"),
                com.example.myapplication.data.model.Member(name = "Suman Rao", role = "Teacher", status = "Present", bio = "Senior Science Teacher", responsibilities = "Lab Maintenance, Science Fair"),
                com.example.myapplication.data.model.Member(name = "Kishore Kumar", role = "Staff", status = "On Leave", bio = "Facility Manager", responsibilities = "Building Repairs, Security")
            )
            memberSamples.forEach { repository.insertMember(it) }
        }
    }

    fun updateAsset(asset: Asset, checkedBy: String? = null, remarks: String? = null) {
        viewModelScope.launch {
            repository.updateAsset(asset)
            if (checkedBy != null) {
                repository.insertConditionHistory(
                    com.example.myapplication.data.model.ConditionHistory(
                        assetId = asset.assetId,
                        status = asset.conditionStatus,
                        checkedBy = checkedBy,
                        checkDate = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date()),
                        remarks = remarks
                    )
                )
            }
        }
    }

    fun deleteAsset(asset: Asset) {
        viewModelScope.launch {
            repository.deleteAsset(asset)
        }
    }

    fun getIssuesForAsset(assetId: Long) = repository.getIssuesForAsset(assetId)
    
    fun getConditionHistoryForAsset(assetId: Long) = repository.getConditionHistoryForAsset(assetId)

    fun logIssue(issue: com.example.myapplication.data.model.IssueLog) {
        viewModelScope.launch {
            repository.insertIssue(issue)
        }
    }

    fun updateIssue(issue: com.example.myapplication.data.model.IssueLog) {
        viewModelScope.launch {
            repository.updateIssue(issue)
        }
    }
}

class AssetViewModelFactory(private val repository: AssetRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
