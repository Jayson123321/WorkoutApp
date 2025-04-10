package com.example.capstoneworkoutapp.ui.screens

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.capstoneworkoutapp.data.model.Pushup
import com.example.capstoneworkoutapp.data.model.Workout
import com.example.capstoneworkoutapp.viewModel.WorkoutViewModel
import com.google.gson.Gson
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutsTodayScreen(
    navController: NavController,
    viewModel: WorkoutViewModel = viewModel()
) {
    val allWorkouts by viewModel.workouts.collectAsState()
    val allPushups by viewModel.pushups.collectAsState()
    val today = LocalDate.now().toString()

    val todayWorkouts = remember(allWorkouts) {
        allWorkouts.filter { it.date == today }
    }

    val todayPushups = remember(allPushups) {
        allPushups.filter { it.date == today }
    }

    LaunchedEffect(Unit) {
        viewModel.loadWorkouts()
        viewModel.loadPushups()
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Today's Workouts") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (todayWorkouts.isEmpty() && todayPushups.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No workouts or push-ups logged today.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(todayWorkouts) { workout ->
                        WorkoutTodayItem(
                            workout = workout,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    items(todayPushups) { pushup ->
                        PushupTodayItem(
                            pushup = pushup,
                            onDelete = { viewModel.deletePushup(pushup.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PushupTodayItem(pushup: Pushup, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    Text("Duur: ${pushup.duration} sec")
                    Text("Categorie: ${pushup.category}")
                }

                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Push-up")
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
fun WorkoutTodayItem(
    workout: Workout,
    navController: NavController,
    viewModel: WorkoutViewModel
) {
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
                    Text("Body Part: ${workout.bodyPart}")
                    Text("Duration: ${workout.duration} min")
                }

                IconButton(onClick = {
                    viewModel.deleteWorkout(workout.id)
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Workout")
                }
            }

            if (workout.setsList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                workout.setsList.forEachIndexed { i, set ->
                    Text("Set ${i + 1}: ${set.sets} x ${set.reps} @ ${set.weight}kg")
                }
            }

            if (workout.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Notes: ${workout.notes}")
            }
        }
    }
}
