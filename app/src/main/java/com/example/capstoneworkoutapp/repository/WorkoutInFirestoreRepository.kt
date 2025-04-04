package com.example.capstoneworkoutapp.repository

import com.example.capstoneworkoutapp.data.api.util.Resource
import com.example.capstoneworkoutapp.data.model.Workout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class WorkoutInFirestoreRepository {
    private val _firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _workoutDocument = _firestore.collection("workouts")

    suspend fun addWorkoutToFireStore(workout: Workout): Resource<String> {
        val data = hashMapOf(
            "category" to workout.category,
            "bodyPart" to workout.bodyPart,
            "exerciseName" to workout.exerciseName,
            "sets" to workout.sets,
            "reps" to workout.reps,
            "weight" to workout.weight,
            "notes" to workout.notes,
            "duration" to workout.duration,
            "date" to workout.date
        )
        return try {
            withTimeout(5_000) {
                _workoutDocument.add(data).await()
            }
            Resource.Success("Workout added successfully")
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred")
        }
    }
    suspend fun getAllWorkouts(): Resource<List<Workout>> {
        return try {
            val snapshot = _workoutDocument.get().await()
            val workouts = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Workout::class.java)
            }
            Resource.Success(workouts)
        } catch (e: Exception) {
            Resource.Error("Failed to load workouts")
        }
    }

}