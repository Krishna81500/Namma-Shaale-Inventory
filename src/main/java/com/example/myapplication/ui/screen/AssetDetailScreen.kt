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
import com.example.myapplication.data.model.Asset
import com.example.myapplication.ui.viewmodel.AssetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailScreen(
    assetId: Long,
    viewModel: AssetViewModel,
    onLogIssueClick: (Long) -> Unit,
    onBack: () -> Unit
) {
    val assets by viewModel.allAssets.collectAsState()
    val asset = assets.find { it.assetId == assetId }
    
    val issues by viewModel.getIssuesForAsset(assetId).collectAsState(initial = emptyList())
    val history by viewModel.getConditionHistoryForAsset(assetId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(asset?.name ?: "Asset Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (asset != null) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Details", style = MaterialTheme.typography.titleLarge)
                Text("Category: ${asset.category}")
                Text("Location: ${asset.location}")
                Text("Quantity: ${asset.quantity}")
                Text("Purchase Date: ${asset.purchaseDate}")
                
                HorizontalDivider()
                
                Text(text = "Condition Status", style = MaterialTheme.typography.titleLarge)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("GREEN", "YELLOW", "RED").forEach { status ->
                        FilterChip(
                            selected = asset.conditionStatus == status,
                            onClick = { 
                                viewModel.updateAsset(asset.copy(conditionStatus = status), "Admin", "Regular Health Check")
                            },
                            label = { Text(status) }
                        )
                    }
                }
                
                HorizontalDivider()
                
                Text(text = "Recent Issues", style = MaterialTheme.typography.titleLarge)
                if (issues.isEmpty()) {
                    Text("No issues reported.")
                } else {
                    issues.forEach { issue ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(text = issue.description, style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Severity: ${issue.severity} • Status: ${issue.repairStatus}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
                
                Button(
                    onClick = { onLogIssueClick(assetId) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Log Maintenance Issue")
                }
                
                HorizontalDivider()
                
                Text(text = "Condition History", style = MaterialTheme.typography.titleLarge)
                if (history.isEmpty()) {
                    Text("No history available.")
                } else {
                    history.forEach { item ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "${item.checkDate}: ${item.status}")
                            Text(text = "by ${item.checkedBy}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Asset not found")
            }
        }
    }
}
