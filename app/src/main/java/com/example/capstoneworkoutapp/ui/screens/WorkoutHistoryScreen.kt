// AddWorkoutFab.kt
package com.example.capstoneworkoutapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.capstoneworkoutapp.ui.screens.WorkoutScreens

@Composable
fun WorkoutHistoryScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        AddWorkoutFab(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
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