package com.example.capstoneworkoutapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capstoneworkoutapp.sensors.StepCounterManager

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutStepsScreen(navController: NavController) {
    val context = LocalContext.current
    val stepManager = remember { StepCounterManager(context) }
    val steps by stepManager.steps.observeAsState(0f)

    LaunchedEffect(Unit) {
        stepManager.startListening()
    }

    DisposableEffect(Unit) {
        onDispose {
            stepManager.stopListening()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Steps Tracker") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Steps:", style = MaterialTheme.typography.titleLarge)
            Text("${steps.toInt()}", style = MaterialTheme.typography.displayLarge)
            Text(
                "Keep app open to track steps",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
