package com.example.capstoneworkoutapp.repository

import com.example.capstoneworkoutapp.data.api.util.Resource
import com.example.capstoneworkoutapp.data.model.Pushup
import com.example.capstoneworkoutapp.data.model.StepLog
import com.example.capstoneworkoutapp.data.model.Workout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class WorkoutInFirestoreRepository {

    private val _firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _workoutDocument = _firestore.collection("workouts")
    private val _pushupDocument = _firestore.collection("pushups")

    suspend fun addWorkoutToFireStore(workout: Workout): Resource<String> {
        val setsMapped = workout.setsList.map { set ->
            mapOf(
                "sets" to set.sets,
                "reps" to set.reps,
                "weight" to set.weight
            )
        }

        val data = hashMapOf(
            "category" to workout.category,
            "bodyPart" to workout.bodyPart,
            "exerciseName" to workout.exerciseName,
            "setsList" to setsMapped,
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
                val workout = doc.toObject(Workout::class.java)
                workout?.copy(id = doc.id)
            }
            Resource.Success(workouts)
        } catch (e: Exception) {
            Resource.Error("Failed to load workouts")
        }
    }

    suspend fun deleteWorkout(workoutId: String): Resource<String> {
        return try {
            _workoutDocument.document(workoutId).delete().await()
            Resource.Success("Workout deleted successfully")
        } catch (e: Exception) {
            Resource.Error("Failed to delete workout")
        }
    }

    suspend fun addPushupToFirestore(pushup: Pushup): Resource<String> {
        val data = hashMapOf(
            "pushupCount" to pushup.pushupCount,
            "duration" to pushup.duration,
            "date" to pushup.date,
            "category" to pushup.category
        )

        return try {
            withTimeout(5_000) {
                _pushupDocument.add(data).await()
            }
            Resource.Success("Push-up saved successfully")
        } catch (e: Exception) {
            Resource.Error("Failed to save push-up session")
        }
    }
    suspend fun getAllPushups(): Resource<List<Pushup>> {
        return try {
            val snapshot = _pushupDocument.get().await()
            val pushups = snapshot.documents.mapNotNull { doc ->
                val pushup = doc.toObject(Pushup::class.java)
                pushup?.copy(id = doc.id)
            }
            Resource.Success(pushups)
        } catch (e: Exception) {
            Resource.Error("Failed to load push-ups")
        }
    }
    suspend fun deletePushup(pushupId: String): Resource<String> {
        return try {
            _pushupDocument.document(pushupId).delete().await()
            Resource.Success("Push-up deleted successfully")
        } catch (e: Exception) {
            Resource.Error("Failed to delete push-up")
        }
    }
    suspend fun saveStepLogToFirestore(stepLog: StepLog): Resource<String> {
        val data = hashMapOf(
            "date" to stepLog.date,
            "stepCount" to stepLog.stepCount
        )

        return try {
            withTimeout(5000) {
                FirebaseFirestore.getInstance()
                    .collection("stepLogs")
                    .add(data).await()
            }
            Resource.Success("Steps saved successfully")
        } catch (e: Exception) {
            Resource.Error("Failed to save steps")
        }
    }


}
