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
import com.example.capstoneworkoutapp.data.model.Pushup
import com.example.capstoneworkoutapp.data.model.Workout
import com.example.capstoneworkoutapp.viewModel.WorkoutViewModel
import com.google.gson.Gson

@Composable
fun WorkoutHistoryScreen(
    navController: NavHostController,
    viewModel: WorkoutViewModel = viewModel()
) {
    val workouts by viewModel.workouts.collectAsState()
    val pushups by viewModel.pushups.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWorkouts()
        viewModel.loadPushups()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (workouts.isEmpty() && pushups.isEmpty()) {
            Text("Geen workouts of push-up sessies gevonden", modifier = Modifier.align(Alignment.Center))
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

                items(pushups) { pushup ->
                    PushupItem(
                        pushup = pushup,
                        navController = navController,
                        onDelete = { viewModel.deletePushup(pushup.id) }
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
fun PushupItem(pushup: Pushup, navController: NavHostController, onDelete: () -> Unit) {
    val pushupJson = Uri.encode(Gson().toJson(pushup))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("PushupDetailScreen/$pushupJson")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Push-ups: ${pushup.pushupCount}", style = MaterialTheme.typography.titleMedium)
                    Text("Duur: ${pushup.duration} sec", style = MaterialTheme.typography.bodyMedium)
                    Text("Categorie: ${pushup.category}", style = MaterialTheme.typography.bodySmall)
                }

                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Pushup")
                }
            }

            Text(
                text = "Datum: ${pushup.date}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
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
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.padding(top = 8.dp)
            )
        }
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
