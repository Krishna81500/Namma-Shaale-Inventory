package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.IssueLog
import com.example.myapplication.ui.viewmodel.AssetViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIssueScreen(
    assetId: Long,
    viewModel: AssetViewModel,
    onBack: () -> Unit
) {
    var description by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf("Medium") }
    
    val severities = listOf("Low", "Medium", "High", "Critical")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log Maintenance Issue") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Issue Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Text("Severity", style = MaterialTheme.typography.bodyLarge)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                severities.forEach { sev ->
                    FilterChip(
                        selected = severity == sev,
                        onClick = { severity = sev },
                        label = { Text(sev) }
                    )
                }
            }
            
            Button(
                onClick = {
                    val issue = IssueLog(
                        assetId = assetId,
                        description = description,
                        severity = severity,
                        dateReported = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date()),
                        repairStatus = "Pending"
                    )
                    viewModel.logIssue(issue)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = description.isNotBlank()
            ) {
                Text("Report Issue")
            }
        }
    }
}
