package com.example.capstoneworkoutapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneworkoutapp.data.api.util.Resource
import com.example.capstoneworkoutapp.data.model.Pushup
import com.example.capstoneworkoutapp.data.model.Workout
import com.example.capstoneworkoutapp.repository.WorkoutInFirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {

    private val repository = WorkoutInFirestoreRepository()

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    private val _saveStatus = MutableStateFlow<Resource<String>?>(null)
    val saveStatus: StateFlow<Resource<String>?> = _saveStatus

    private val _deleteStatus = MutableStateFlow<Resource<String>?>(null)
    val deleteStatus: StateFlow<Resource<String>?> = _deleteStatus

    private val _pushups = MutableStateFlow<List<Pushup>>(emptyList())
    val pushups: StateFlow<List<Pushup>> = _pushups


    fun saveWorkout(workout: Workout) {
        viewModelScope.launch {
            _saveStatus.value = Resource.Loading()
            _saveStatus.value = repository.addWorkoutToFireStore(workout)
        }
    }

    fun savePushup(pushup: Pushup) {
        viewModelScope.launch {
            _saveStatus.value = Resource.Loading()
            _saveStatus.value = repository.addPushupToFirestore(pushup)
        }
    }

    fun loadWorkouts() {
        viewModelScope.launch {
            when (val result = repository.getAllWorkouts()) {
                is Resource.Success -> _workouts.value = result.data ?: emptyList()
                is Resource.Error -> _workouts.value = emptyList()
                else -> {}
            }
        }
    }

    fun deleteWorkout(workoutId: String) {
        viewModelScope.launch {
            _deleteStatus.value = Resource.Loading()
            _deleteStatus.value = repository.deleteWorkout(workoutId)
            loadWorkouts()
        }
    }
    fun deletePushup(pushupId: String) {
        viewModelScope.launch {
            _deleteStatus.value = Resource.Loading()
            _deleteStatus.value = repository.deletePushup(pushupId)
            loadPushups()
        }
    }
    fun loadPushups() {
        viewModelScope.launch {
            when (val result = repository.getAllPushups()) {
                is Resource.Success -> _pushups.value = result.data ?: emptyList()
                is Resource.Error -> _pushups.value = emptyList()
                else -> {}
            }
        }
    }

    fun resetSaveStatus() {
        _saveStatus.value = null
    }

    fun resetDeleteStatus() {
        _deleteStatus.value = null
    }
}
