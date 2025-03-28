package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.TaskListScreen

@Composable
fun NavGraph() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("taskList") { TaskListScreen(navController) }
    }
}
