package com.example.capstoneworkoutapp.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.capstoneworkoutapp.data.model.Pushup
import com.example.capstoneworkoutapp.viewModel.WorkoutViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PushupsScreen(navController: NavHostController, vm: WorkoutViewModel = viewModel()) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    var pushupCount by remember { mutableIntStateOf(0) }
    var isTracking by remember { mutableStateOf(false) }
    var duration by remember { mutableIntStateOf(0) }


    LaunchedEffect(isTracking) {
        if (isTracking) {
            duration = 0
            while (isTracking) {
                delay(1000)
                duration++
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Push-up Counter") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            PushupCounterDisplay(pushupCount = pushupCount, duration = duration)
            Spacer(modifier = Modifier.height(32.dp))
            StartStopButtons(
                isTracking = isTracking,
                onStart = {
                    pushupCount = 0
                    isTracking = true
                },
                onStop = {
                    isTracking = false
                },
                onSave = {
                    val pushup = Pushup(
                        id = "",
                        pushupCount = pushupCount,
                        date = LocalDate.now().toString(),
                        duration = duration,
                        category = "Push-ups"
                    )
                    vm.savePushup(pushup)
                }
            )
        }
    }

    if (isTracking) {
        ProximityPushupSensorHandler(
            sensorManager = sensorManager,
            onPushupDetected = { pushupCount++ }
        )
    }
}

@Composable
fun PushupCounterDisplay(pushupCount: Int, duration: Int) {
    Text(text = "Push-ups:", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "$pushupCount", style = MaterialTheme.typography.displayLarge)
    Spacer(modifier = Modifier.height(24.dp))
    Text(text = "Tijd: $duration sec", style = MaterialTheme.typography.bodyLarge)
}

@Composable
fun StartStopButtons(
    isTracking: Boolean,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onSave: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!isTracking) {
            Button(onClick = onStart) {
                Text("Start")
            }
            if (onSave != null) {
                Button(onClick = onSave) {
                    Text("Opslaan")
                }
            }
        } else {
            Button(
                onClick = onStop,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Stoppen")
            }
        }
    }
}

@Composable
fun ProximityPushupSensorHandler(
    sensorManager: SensorManager,
    onPushupDetected: () -> Unit
) {
    var isNear by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                val value = event?.values?.get(0) ?: return

                if (proximitySensor != null) {
                    if (!isNear && value < proximitySensor.maximumRange) {
                        isNear = true
                    } else if (isNear && value >= proximitySensor.maximumRange) {
                        isNear = false
                        onPushupDetected()
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }
}
