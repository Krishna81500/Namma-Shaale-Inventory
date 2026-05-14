package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Asset
import com.example.myapplication.ui.viewmodel.AssetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuditModeScreen(
    viewModel: AssetViewModel,
    onBack: () -> Unit
) {
    val assets by viewModel.allAssets.collectAsState()
    var auditedCount by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monthly Health Check") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        text = "$auditedCount / ${assets.size} Checked",
                        modifier = Modifier.padding(end = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(assets) { asset ->
                AuditItemCard(
                    asset = asset,
                    onStatusUpdate = { newStatus ->
                        viewModel.updateAsset(
                            asset.copy(conditionStatus = newStatus),
                            checkedBy = "Teacher",
                            remarks = "Monthly Audit"
                        )
                        auditedCount++
                    }
                )
            }
        }
    }
}

@Composable
fun AuditItemCard(
    asset: Asset,
    onStatusUpdate: (String) -> Unit
) {
    var selectedStatus by remember { mutableStateOf(asset.conditionStatus) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = asset.name, style = MaterialTheme.typography.titleMedium)
            Text(text = asset.location, style = MaterialTheme.typography.bodySmall)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusButton(
                    label = "Working",
                    color = Color.Green,
                    isSelected = selectedStatus == "GREEN",
                    onClick = {
                        selectedStatus = "GREEN"
                        onStatusUpdate("GREEN")
                    }
                )
                StatusButton(
                    label = "Repair",
                    color = Color.Yellow,
                    isSelected = selectedStatus == "YELLOW",
                    onClick = {
                        selectedStatus = "YELLOW"
                        onStatusUpdate("YELLOW")
                    }
                )
                StatusButton(
                    label = "Broken",
                    color = Color.Red,
                    isSelected = selectedStatus == "RED",
                    onClick = {
                        selectedStatus = "RED"
                        onStatusUpdate("RED")
                    }
                )
            }
        }
    }
}

@Composable
fun StatusButton(
    label: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else color.copy(alpha = 0.1f),
            contentColor = if (isSelected) Color.White else color
        ),
        modifier = Modifier.width(90.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}
