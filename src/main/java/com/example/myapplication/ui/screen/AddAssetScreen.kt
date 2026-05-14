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
import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

@Preview(showBackground = true)
@Composable
fun AddAssetPreview() {
    MyApplicationTheme {
        Text("Add Asset Preview")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssetScreen(
    viewModel: AssetViewModel,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Furniture") }
    var location by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    
    val categories = listOf("Furniture", "Electronics", "Books", "Lab", "Sports")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Asset") },
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
                value = name,
                onValueChange = { name = it },
                label = { Text("Asset Name") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Text("Category", style = MaterialTheme.typography.bodyLarge)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = category == cat,
                        onClick = { category = cat },
                        label = { Text(cat) }
                    )
                }
            }
            
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location (Room/Block)") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Button(
                onClick = {
                    val asset = Asset(
                        name = name,
                        category = category,
                        location = location,
                        quantity = quantity.toIntOrNull() ?: 1,
                        purchaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                        conditionStatus = "GREEN"
                    )
                    viewModel.addAsset(asset)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && location.isNotBlank()
            ) {
                Text("Register Asset")
            }
        }
    }
}
