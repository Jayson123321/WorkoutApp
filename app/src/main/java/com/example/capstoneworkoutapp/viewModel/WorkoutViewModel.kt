package com.example.capstoneworkoutapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneworkoutapp.data.model.Workout
import com.example.capstoneworkoutapp.repository.WorkoutInFirestoreRepository
import com.example.capstoneworkoutapp.data.api.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {

    private val repository = WorkoutInFirestoreRepository()

    // Alle workouts (voor WorkoutHistoryScreen)
    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    // Status van save actie (voor WorkoutAddScreen)
    private val _saveStatus = MutableStateFlow<Resource<String>?>(null)
    val saveStatus: StateFlow<Resource<String>?> = _saveStatus

    // Workout opslaan in Firestore
    fun saveWorkout(workout: Workout) {
        viewModelScope.launch {
            _saveStatus.value = Resource.Loading()
            _saveStatus.value = repository.addWorkoutToFireStore(workout)
        }
    }

    // Workouts ophalen uit Firestore
    fun loadWorkouts() {
        viewModelScope.launch {
            when (val result = repository.getAllWorkouts()) {
                is Resource.Success -> _workouts.value = result.data ?: emptyList()
                is Resource.Error -> _workouts.value = emptyList()
                else -> {}
            }
        }
    }

    // Reset status na gebruik
    fun resetSaveStatus() {
        _saveStatus.value = null
    }
}
