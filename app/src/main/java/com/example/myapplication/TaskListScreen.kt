package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.model.Task

@Composable
fun TaskListScreen(navController: NavController) {
    val tasks = listOf(
        Task(1, "Buy groceries"),
        Task(2, "Finish project"),
        Task(3, "Call mom"),
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Your Tasks") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(task, navController)
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("task_detail/${task.id}")
            },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(task.name, fontSize = 18.sp)
        }
    }
}
