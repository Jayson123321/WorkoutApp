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

    private val _saveStatus = MutableStateFlow<Resource<String>?>(null)
    val saveStatus: StateFlow<Resource<String>?> = _saveStatus

    fun saveWorkout(workout: Workout) {
        viewModelScope.launch {
            _saveStatus.value = repository.addWorkoutToFireStore(workout)
        }
    }
}
