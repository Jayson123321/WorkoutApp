package com.example.capstoneworkoutapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.capstoneworkoutapp.data.model.Pushup
import com.example.capstoneworkoutapp.data.model.Workout
import com.example.capstoneworkoutapp.ui.screens.PushupDetailScreen
import com.example.capstoneworkoutapp.ui.screens.PushupsScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutHistoryScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutAddScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutsDetailScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutFavouriteScreen
import com.example.capstoneworkoutapp.ui.screens.WorkoutScreens
import com.example.capstoneworkoutapp.ui.screens.WorkoutStepsScreen
import com.example.capstoneworkoutapp.ui.theme.CapstoneWorkoutappTheme
import com.example.capstoneworkoutapp.ui.screens.WorkoutsTodayScreen
import com.google.gson.Gson


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapstoneWorkoutappTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController = navController) },
                    floatingActionButton = { AddWorkoutFab(navController = navController) }
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
        NavigationBar(
            containerColor = Color.White
        ) {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route

            NavigationBarItem(
                icon = {  },
                label = { Text("Today") },
                selected = currentRoute == WorkoutScreens.WorkoutsTodayScreen.name,
                onClick = {
                    navController.navigate(WorkoutScreens.WorkoutsTodayScreen.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            )

            NavigationBarItem(
                icon = {  },
                label = { Text("History") },
                selected = currentRoute == WorkoutScreens.WorkoutHistoryScreen.name,
                onClick = {
                    navController.navigate(WorkoutScreens.WorkoutHistoryScreen.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
            NavigationBarItem(
                icon = {  },
                label = { Text("Steps") },
                selected = currentRoute == WorkoutScreens.WorkoutStepsScreen.name,
                onClick = {
                    navController.navigate(WorkoutScreens.WorkoutStepsScreen.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
            NavigationBarItem(
                icon = {  },
                label = { Text("Pushups") },
                selected = currentRoute == WorkoutScreens.PushupsScreen.name,
                onClick = {
                    navController.navigate(WorkoutScreens.PushupsScreen.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
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
            composable(route = WorkoutScreens.WorkoutAddScreens.name) {
                WorkoutAddScreen(navController = navController)
            }
            composable(route = WorkoutScreens.WorkoutFavouriteScreen.name) {
                WorkoutFavouriteScreen(navController = navController)
            }
            composable(route = WorkoutScreens.WorkoutHistoryScreen.name) {
                WorkoutHistoryScreen(navController = navController)
            }
            composable(route = WorkoutScreens.WorkoutsTodayScreen.name) {
                WorkoutsTodayScreen(navController = navController)
            }
            composable(route = WorkoutScreens.WorkoutStepsScreen.name) {
                WorkoutStepsScreen(navController = navController)
            }
            composable(route = WorkoutScreens.PushupsScreen.name) {
                PushupsScreen(navController = navController, vm = viewModel())
            }
            composable(
                route = "PushupDetailScreen/{pushupJson}",
                arguments = listOf(navArgument("pushupJson") { type = NavType.StringType })
            ) { backStackEntry ->
                val pushupJson = backStackEntry.arguments?.getString("pushupJson")
                val pushup = Gson().fromJson(pushupJson, Pushup::class.java)
                PushupDetailScreen(navController = navController, pushup = pushup)
            }

            composable(
                route = "WorkoutDetailScreen/{workoutJson}",
                arguments = listOf(navArgument("workoutJson") { type = NavType.StringType })
            ) { backStackEntry ->
                val workoutJson = backStackEntry.arguments?.getString("workoutJson")
                val workout = Gson().fromJson(workoutJson, Workout::class.java)
                WorkoutsDetailScreen(navController = navController, workout = workout)
            }
        }
    }


}
@Composable
fun AddWorkoutFab(navController: NavController, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { navController.navigate(WorkoutScreens.WorkoutAddScreens.name) },
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Workout")
    }
}