package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.viewmodel.AssetViewModel

import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    MyApplicationTheme {
        // We can't easily pass the real ViewModel, but we can mock or use a dummy for preview
        // For now, this is just to check if @Preview works
        Text("Dashboard Preview")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: AssetViewModel,
    onAddAssetClick: () -> Unit,
    onAssetClick: (Long) -> Unit,
    onReportClick: () -> Unit,
    onMemberClick: () -> Unit,
    onAuditClick: () -> Unit
) {
    val assetCount by viewModel.assetCount.collectAsState()
    val pendingIssueCount by viewModel.pendingIssueCount.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Namma-Shaale Dashboard") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAssetClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Asset")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "School Overview",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "Total Assets",
                    count = assetCount.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Pending Issues",
                    count = pendingIssueCount.toString(),
                    color = if (pendingIssueCount > 0) Color.Red else Color.Green,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = onAuditClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("Start Monthly Health Check")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onReportClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate AI Audit Report")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onMemberClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Member & Attendance Management")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { viewModel.seedSampleData() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seed Sample Assets")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Recent Assets",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            val assets by viewModel.allAssets.collectAsState()
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(assets.take(5).size) { index ->
                    val asset = assets[index]
                    AssetCard(asset = asset, onClick = { onAssetClick(asset.assetId) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetCard(asset: com.example.myapplication.data.model.Asset, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = asset.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${asset.category} • ${asset.location}", style = MaterialTheme.typography.bodySmall)
            }
            ConditionBadge(status = asset.conditionStatus)
        }
    }
}

@Composable
fun ConditionBadge(status: String) {
    val color = when (status) {
        "GREEN" -> Color.Green
        "YELLOW" -> Color.Yellow
        "RED" -> Color.Red
        else -> Color.Gray
    }
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small,
        border = androidx.compose.foundation.BorderStroke(1.dp, color)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = if (status == "YELLOW") Color.DarkGray else color
        )
    }
}

@Composable
fun StatCard(
    title: String,
    count: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Card(
        modifier = modifier.height(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = count,
                style = MaterialTheme.typography.headlineMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
