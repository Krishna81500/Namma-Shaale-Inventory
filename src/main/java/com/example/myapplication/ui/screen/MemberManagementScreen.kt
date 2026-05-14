package com.example.myapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Member
import com.example.myapplication.ui.viewmodel.AssetViewModel

import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

@Preview(showBackground = true)
@Composable
fun MemberCardPreview() {
    MyApplicationTheme {
        MemberCard(
            member = Member(
                name = "Test User",
                role = "Teacher",
                status = "Present",
                bio = "Bio test",
                responsibilities = "Testing"
            ),
            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberManagementScreen(
    viewModel: AssetViewModel,
    onBack: () -> Unit
) {
    val members by viewModel.allMembers.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Member Management") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Add Member Dialog */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Member")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(members) { member ->
                MemberCard(member = member, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MemberCard(member: Member, viewModel: AssetViewModel) {
    val attendance by viewModel.getAttendanceForMember(member.memberId).collectAsState(initial = emptyList())

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = member.name, style = MaterialTheme.typography.titleLarge)
                    Text(text = member.role, style = MaterialTheme.typography.bodyMedium)
                }
                StatusBadge(status = member.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Responsibilities: ${member.responsibilities}", style = MaterialTheme.typography.bodySmall)
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "30-Day Attendance Heatmap", style = MaterialTheme.typography.labelMedium)
            AttendanceHeatmap(attendance = attendance)
            
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Present", "Absent", "On Leave").forEach { status ->
                    AssistChip(
                        onClick = { 
                            viewModel.updateMember(member.copy(status = status))
                            viewModel.logAttendance(
                                com.example.myapplication.data.model.Attendance(
                                    memberId = member.memberId,
                                    date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date()),
                                    status = status
                                )
                            )
                        },
                        label = { Text(status) }
                    )
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val color = when (status) {
        "Present" -> Color.Green
        "Absent" -> Color.Red
        "On Leave" -> Color.Yellow
        else -> Color.Gray
    }
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = if (status == "On Leave") Color.DarkGray else color
        )
    }
}

@Composable
fun AttendanceHeatmap(attendance: List<com.example.myapplication.data.model.Attendance>) {
    // Simplified 30-day grid (5x6)
    LazyVerticalGrid(
        columns = GridCells.Fixed(10),
        modifier = Modifier.height(60.dp),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(30) { index ->
            val color = Color.LightGray // Default
            // Logic to find attendance for this 'day' index would go here
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color, RoundedCornerShape(2.dp))
            )
        }
    }
}
