package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screen.DashboardScreen
import com.example.myapplication.ui.screen.AddAssetScreen
import com.example.myapplication.ui.screen.ReportScreen
import com.example.myapplication.ui.screen.AssetDetailScreen
import com.example.myapplication.ui.screen.LogIssueScreen
import com.example.myapplication.ui.screen.MemberManagementScreen
import com.example.myapplication.ui.screen.AuditModeScreen
import com.example.myapplication.ui.screen.LoginScreen
import com.example.myapplication.ui.screen.RegisterScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.AssetViewModel
import com.example.myapplication.ui.viewmodel.AssetViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: AssetViewModel by viewModels {
        AssetViewModelFactory((application as NammaShaaleApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                viewModel = viewModel,
                                onLoginSuccess = { 
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onRegisterClick = {
                                    navController.navigate("register")
                                }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                viewModel = viewModel,
                                onRegisterSuccess = {
                                    navController.popBackStack()
                                },
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable("dashboard") {
                            DashboardScreen(
                                viewModel = viewModel,
                                onAddAssetClick = { navController.navigate("add_asset") },
                                onAssetClick = { assetId -> navController.navigate("asset_detail/$assetId") },
                                onReportClick = { navController.navigate("report") },
                                onMemberClick = { navController.navigate("members") },
                                onAuditClick = { navController.navigate("audit") }
                            )
                        }
                        composable("audit") {
                            AuditModeScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("members") {
                            MemberManagementScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("add_asset") {
                            AddAssetScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("report") {
                            ReportScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable(
                            route = "asset_detail/{assetId}",
                            arguments = listOf(navArgument("assetId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val assetId = backStackEntry.arguments?.getLong("assetId") ?: 0L
                            AssetDetailScreen(
                                assetId = assetId,
                                viewModel = viewModel,
                                onLogIssueClick = { id -> navController.navigate("log_issue/$id") },
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable(
                            route = "log_issue/{assetId}",
                            arguments = listOf(navArgument("assetId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val assetId = backStackEntry.arguments?.getLong("assetId") ?: 0L
                            LogIssueScreen(
                                assetId = assetId,
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
