package com.example.capstoneworkoutapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capstoneworkoutapp.data.model.Workout
import com.example.capstoneworkoutapp.viewModel.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutsDetailScreen(
    navController: NavController,
    workout: Workout,
    viewModel: WorkoutViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Details") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // ✅ Scroll support
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Exercise: ${workout.exerciseName}", style = MaterialTheme.typography.titleLarge)
                    Text("Body Part: ${workout.bodyPart}")
                    Text("Duration: ${workout.duration} min")
                    Text("Date: ${workout.date}")
                }
            }

            if (workout.setsList.isNotEmpty()) {
                Text("Sets:", style = MaterialTheme.typography.titleMedium)
                workout.setsList.forEachIndexed { index, set ->
                    Text("Set ${index + 1}: ${set.sets} x ${set.reps} @ ${set.weight}kg")
                }
            }

            if (workout.notes.isNotBlank()) {
                Text("Notes:", style = MaterialTheme.typography.titleMedium)
                Text(workout.notes)
            }
        }
    }
}
