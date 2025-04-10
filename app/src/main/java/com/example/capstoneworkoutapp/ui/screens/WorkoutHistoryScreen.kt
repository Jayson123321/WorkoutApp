package com.example.capstoneworkoutapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.capstoneworkoutapp.viewModel.WorkoutViewModel
import com.example.capstoneworkoutapp.data.model.Workout
import com.google.gson.Gson

@Composable
fun WorkoutHistoryScreen(
    navController: NavHostController,
    viewModel: WorkoutViewModel = viewModel()
) {
    val workouts by viewModel.workouts.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadWorkouts()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (workouts.isEmpty()) {
            Text("Geen workouts gevonden", modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(workouts) { workout ->
                    WorkoutItem(
                        workout = workout,
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }

        AddWorkoutFab(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        )
    }
}

@Composable
fun WorkoutItem(workout: Workout, navController: NavHostController, viewModel: WorkoutViewModel) {
    val workoutJson = Uri.encode(Gson().toJson(workout))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("WorkoutDetailScreen/$workoutJson")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Exercise: ${workout.exerciseName}", style = MaterialTheme.typography.titleMedium)
                Text("Category: ${workout.category}", style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(onClick = {
                viewModel.deleteWorkout(workout.id)
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Workout")
            }
        }
        Text(
            text = "Date: ${workout.date}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.End).padding(16.dp)
        )
    }
}

@Composable
fun AddWorkoutFab(navController: NavHostController, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { navController.navigate(WorkoutScreens.WorkoutAddScreens.name) },
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Workout")
    }
}
