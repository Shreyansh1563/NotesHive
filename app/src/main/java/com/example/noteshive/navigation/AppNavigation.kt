package com.example.noteshive.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noteshive.screens.HomeScreen
import com.example.noteshive.screens.NotesScreen
import com.example.noteshive.screens.SubjectSelectScreen
import com.example.noteshive.screens.YearSelectScreen
import com.example.noteshive.viewModel.OptionsViewModel

@Composable
fun AppNavigation(modifier: Modifier= Modifier, navController: NavHostController, viewModel: OptionsViewModel){
    NavHost(navController = navController, startDestination = AppNavigationItems.BranchSelectionScreen.route){

        composable(route = AppNavigationItems.BranchSelectionScreen.route){
            HomeScreen(modifier, navController, viewModel)
        }

        composable(route = AppNavigationItems.YearSelectionScreen.route){
            YearSelectScreen(modifier = modifier, navController, viewModel)
        }

        composable(route = AppNavigationItems.SubjectSelectionScreen.route){
            SubjectSelectScreen(modifier = modifier, navController, viewModel)
        }

        composable(route = AppNavigationItems.NotesSelectionScreen.route){
            NotesScreen(modifier= modifier, viewModel)
        }
    }
}