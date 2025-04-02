package com.example.capstoneworkoutapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.capstoneworkoutapp.ui.components.WorkoutHistoryScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutAddScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutFavouriteScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutScreens
import com.example.capstoneworkoutapp.ui.theme.CapstoneWorkoutappTheme
import com.example.workouttracking.ui.screens.WorkoutsTodayScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapstoneWorkoutappTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) { paddingValues ->
                    NavHost(
                        navController = navController, modifier = Modifier.padding(paddingValues)
                    )

                }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val items = listOf(
            WorkoutScreens.WorkoutsTodayScreen,
            WorkoutScreens.WorkoutHistoryScreen,
        )
        NavigationBar(
            containerColor = Color.White
        ) {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route
            items.forEach { screen ->
                NavigationBarItem(
                    icon = {  },
                    label = { Text(if (screen == WorkoutScreens.WorkoutsTodayScreen) "History" else "Today") },
                    selected = currentRoute == screen.name,
                    onClick = {
                        navController.navigate(screen.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
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
            composable(route = WorkoutScreens.WorkoutsTodayScreen.name) {
                WorkoutsTodayScreen(navController = navController)
            }
        }
    }
}