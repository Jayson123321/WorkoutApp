package com.example.capstoneworkoutapp.data.model

data class Workout(
    val category: String = "",
    val bodyPart: String = "",
    val exerciseName: String = "",
    val setsList: List<ExerciseSet> = emptyList(),
    val notes: String = "",
    val duration: String = "",
    val date: String = ""
)
