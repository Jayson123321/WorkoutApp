package com.example.capstoneworkoutapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun WorkoutAddScreen(navController: NavHostController) {
    var category by remember { mutableStateOf("") }
    var bodyPart by remember { mutableStateOf("") }
    var exerciseName by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Category(
            category = category,
            onCategorySelected = { category = it },
            bodyPart = bodyPart
        )
        ExerciseDetails(
            exerciseName = exerciseName,
            sets = sets,
            reps = reps,
            weight = weight
        )
        AfterWorkout(
            notes = notes,
            duration = duration
        )
        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Workout")
        }
    }
}

@Composable
fun Category(category: String, onCategorySelected: (String) -> Unit, bodyPart: String) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(category) }
    var bodyPartState by remember { mutableStateOf(bodyPart) }
    val categories = listOf("Cardio", "Strength")

    Column {
        Text(
            text = selectedCategory,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(8.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(text = category) },
                    onClick = {
                        selectedCategory = category
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
        TextField(
            value = bodyPartState,
            onValueChange = { bodyPartState = it },
            label = { Text("Body Part") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ExerciseDetails(
    exerciseName: String,
    sets: String,
    reps: String,
    weight: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = exerciseName,
            onValueChange = {  },
            label = { Text("Exercise Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = sets,
            onValueChange = {  },
            label = { Text("Sets") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = reps,
            onValueChange = {  },
            label = { Text("Reps") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = weight,
            onValueChange = {  },
            label = { Text("Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AfterWorkout(notes: String, duration: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = notes,
            onValueChange = {  },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = duration,
            onValueChange = {  },
            label = { Text("Duration") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}