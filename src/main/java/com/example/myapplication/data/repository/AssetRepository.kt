package com.example.myapplication.data.repository

import com.example.myapplication.data.local.AssetDao
import com.example.myapplication.data.model.Asset
import com.example.myapplication.data.model.ConditionHistory
import com.example.myapplication.data.model.IssueLog
import kotlinx.coroutines.flow.Flow

class AssetRepository(private val assetDao: AssetDao) {
    fun getAllAssets(): Flow<List<Asset>> = assetDao.getAllAssets()
    
    suspend fun getAssetById(id: Long): Asset? = assetDao.getAssetById(id)
    
    suspend fun insertAsset(asset: Asset) = assetDao.insertAsset(asset)
    
    suspend fun updateAsset(asset: Asset) = assetDao.updateAsset(asset)
    
    suspend fun deleteAsset(asset: Asset) = assetDao.deleteAsset(asset)
    
    fun getIssuesForAsset(assetId: Long): Flow<List<IssueLog>> = assetDao.getIssuesForAsset(assetId)
    
    suspend fun insertIssue(issue: IssueLog) = assetDao.insertIssue(issue)
    
    suspend fun updateIssue(issue: IssueLog) = assetDao.updateIssue(issue)
    
    fun getConditionHistoryForAsset(assetId: Long): Flow<List<ConditionHistory>> = 
        assetDao.getConditionHistoryForAsset(assetId)
        
    suspend fun insertConditionHistory(history: ConditionHistory) = 
        assetDao.insertConditionHistory(history)

    fun getAssetCount(): Flow<Int> = assetDao.getAssetCount()
    
    fun getPendingIssueCount(): Flow<Int> = assetDao.getPendingIssueCount()

    // Member Management
    fun getAllMembers() = assetDao.getAllMembers()
    suspend fun insertMember(member: com.example.myapplication.data.model.Member) = assetDao.insertMember(member)
    suspend fun updateMember(member: com.example.myapplication.data.model.Member) = assetDao.updateMember(member)
    fun getAttendanceForMember(memberId: Long) = assetDao.getAttendanceForMember(memberId)
    suspend fun insertAttendance(attendance: com.example.myapplication.data.model.Attendance) = assetDao.insertAttendance(attendance)

    // User Authentication
    suspend fun registerUser(user: com.example.myapplication.data.model.User) = assetDao.registerUser(user)
    suspend fun getUserByEmail(email: String) = assetDao.getUserByEmail(email)
    suspend fun getUserByUniqueId(uniqueId: String) = assetDao.getUserByUniqueId(uniqueId)
}
