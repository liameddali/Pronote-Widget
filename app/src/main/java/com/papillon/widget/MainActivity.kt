package com.papillon.widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.papillon.widget.ui.screens.DashboardScreen
import com.papillon.widget.ui.screens.LoginScreen
import com.papillon.widget.ui.screens.SchoolScreen
import com.papillon.widget.ui.screens.WelcomeScreen
import com.papillon.widget.ui.theme.PapillonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PapillonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PapillonApp()
                }
            }
        }
    }
}

@Composable
fun PapillonApp() {
    val navController = rememberNavController()
    var selectedSchoolName by remember { mutableStateOf<String?>(null) }
    var selectedSchoolUrl by remember { mutableStateOf<String?>(null) }
    
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onStart = { navController.navigate("school") }
            )
        }
        composable("school") {
            SchoolScreen(
                onSchoolSelected = { name, url ->
                    selectedSchoolName = name
                    selectedSchoolUrl = url
                    navController.navigate("login")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("login") {
            LoginScreen(
                schoolName = selectedSchoolName,
                schoolUrl = selectedSchoolUrl,
                onLoginSuccess = { navController.navigate("dashboard") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onLogout = {
                    navController.navigate("welcome") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }
    }
}
