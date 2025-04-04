package com.example.workouttracking.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.capstoneworkoutapp.data.model.Workout
import com.example.capstoneworkoutapp.viewModel.WorkoutViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutsTodayScreen(
    navController: NavController,
    viewModel: WorkoutViewModel = viewModel()
) {
    val allWorkouts by viewModel.workouts.collectAsState()
    val today = LocalDate.now().toString()

    val todayWorkouts = remember(allWorkouts) {
        allWorkouts.filter { it.date == today }
    }

    LaunchedEffect(Unit) {
        viewModel.loadWorkouts()
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
            if (todayWorkouts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No workouts logged today.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(todayWorkouts) { workout ->
                        WorkoutTodayItem(workout)
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutTodayItem(workout: Workout) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Exercise: ${workout.exerciseName}", style = MaterialTheme.typography.titleMedium)
            Text("Body Part: ${workout.bodyPart}")
            Text("Duration: ${workout.duration} min")
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
