package com.example.capstoneworkoutapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.capstoneworkoutapp.data.model.ExerciseSet
import com.example.capstoneworkoutapp.data.model.Workout
import com.example.capstoneworkoutapp.data.api.util.Resource
import com.example.capstoneworkoutapp.viewModel.WorkoutViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutAddScreen(
    navController: NavHostController,
    viewModel: WorkoutViewModel = viewModel()
) {
    var category by remember { mutableStateOf("") }
    var bodyPart by remember { mutableStateOf("") }
    var exerciseName by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    var setsList by remember {
        mutableStateOf(
            mutableListOf(
                ExerciseSet("", "", "")
            )
        )
    }

    val saveStatus by viewModel.saveStatus.collectAsState()

    LaunchedEffect(saveStatus) {
        if (saveStatus is Resource.Success) {
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        WorkoutInfoSection(
            category = category,
            bodyPart = bodyPart,
            onCategoryChange = { category = it },
            onBodyPartChange = { bodyPart = it }
        )

        ExerciseNameSection(
            exerciseName = exerciseName,
            onExerciseNameChange = { exerciseName = it }
        )

        ExerciseSetsSection(
            setsList = setsList,
            onSetsListChange = { setsList = it.toMutableList() }
        )

        PostWorkoutSection(
            notes = notes,
            duration = duration,
            onNotesChange = { notes = it },
            onDurationChange = { duration = it }
        )

        Button(
            onClick = {
                val workout = Workout(
                    category = category,
                    bodyPart = bodyPart,
                    exerciseName = exerciseName,
                    setsList = setsList,
                    notes = notes,
                    duration = duration,
                    date = LocalDate.now().toString()
                )
                viewModel.saveWorkout(workout)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Workout")
        }

        if (saveStatus is Resource.Loading) {
            CircularProgressIndicator(Modifier.padding(top = 8.dp))
        }
        if (saveStatus is Resource.Error) {
            Text(
                text = (saveStatus as Resource.Error).message ?: "Error saving workout",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}


@Composable
fun WorkoutInfoSection(
    category: String,
    bodyPart: String,
    onCategoryChange: (String) -> Unit,
    onBodyPartChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Cardio", "Strength")

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("Workout Info", style = MaterialTheme.typography.titleMedium)

            Text(
                text = if (category.isNotEmpty()) category else "Select Category",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(8.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onCategoryChange(option)
                            expanded = false
                        }
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            TextField(
                value = bodyPart,
                onValueChange = onBodyPartChange,
                label = { Text("Body Part") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ExerciseNameSection(
    exerciseName: String,
    onExerciseNameChange: (String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            TextField(
                value = exerciseName,
                onValueChange = onExerciseNameChange,
                label = { Text("Exercise Name") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ExerciseSetsSection(
    setsList: List<ExerciseSet>,
    onSetsListChange: (List<ExerciseSet>) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Exercise Sets", style = MaterialTheme.typography.titleMedium)

            setsList.forEachIndexed { index, set ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = set.sets,
                        onValueChange = {
                            val newList = setsList.toMutableList()
                            newList[index] = set.copy(sets = it.filter { c -> c.isDigit() })
                            onSetsListChange(newList)
                        },
                        label = { Text("Set") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = set.reps,
                        onValueChange = {
                            val newList = setsList.toMutableList()
                            newList[index] = set.copy(reps = it.filter { c -> c.isDigit() })
                            onSetsListChange(newList)
                        },
                        label = { Text("Reps") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = set.weight,
                        onValueChange = {
                            val newList = setsList.toMutableList()
                            newList[index] = set.copy(weight = it.filter { c -> c.isDigit() })
                            onSetsListChange(newList)
                        },
                        label = { Text("Weight") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            TextButton(onClick = {
                onSetsListChange(setsList.toMutableList().apply {
                    add(ExerciseSet("", "", ""))
                })
            }) {
                Text("+ Add Set")
            }
        }
    }
}

@Composable
fun PostWorkoutSection(
    notes: String,
    duration: String,
    onNotesChange: (String) -> Unit,
    onDurationChange: (String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = notes,
                onValueChange = onNotesChange,
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = duration,
                onValueChange = { onDurationChange(it.filter { c -> c.isDigit() }) },
                label = { Text("Duration (min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
