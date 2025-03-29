package com.example.capstoneworkoutapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capstoneworkoutapp.ui.components.WorkoutHistoryScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutAddScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutFavouriteScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutScreens
import com.example.capstoneworkoutapp.ui.theme.CapstoneWorkoutappTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapstoneWorkoutappTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    NavHost(
                        navController = navController, modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }


    @Composable
    fun NavHost(
        navController: NavHostController,
        modifier: Modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = WorkoutScreens.WorkoutHistoryScreen.name,
            modifier = modifier
        ) {
            composable(route = WorkoutScreens.WorkoutDetailScreen.name) {
                WorkoutAddScreen(navController = navController)
            }
            composable(route = WorkoutScreens.WorkoutFavouriteScreen.name) {
                WorkoutFavouriteScreen(navController = navController)
            }
            composable(route = WorkoutScreens.WorkoutAddScreens.name) {
                WorkoutAddScreen(navController = navController)
            }
            composable(route = WorkoutScreens.WorkoutHistoryScreen.name) {
                WorkoutHistoryScreen(navController = navController)
            }
        }
    }
}