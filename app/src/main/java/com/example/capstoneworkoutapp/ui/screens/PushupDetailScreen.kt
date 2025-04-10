package com.example.capstoneworkoutapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capstoneworkoutapp.data.model.Pushup

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PushupDetailScreen(
    navController: NavController,
    pushup: Pushup
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Push-up Details") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // 👈 scroll enabled
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Push-ups: ${pushup.pushupCount}", style = MaterialTheme.typography.titleLarge)
            Text("Duration: ${pushup.duration} sec")
            Text("Date: ${pushup.date}")
            Text("Category: ${pushup.category}")
        }
    }
}
