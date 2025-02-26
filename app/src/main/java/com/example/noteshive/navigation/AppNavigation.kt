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

@Composable
fun AppNavigation(modifier: Modifier= Modifier, navController: NavHostController){
    NavHost(navController = navController, startDestination = AppNavigationItems.BranchSelectionScreen.route){

        composable(route = AppNavigationItems.BranchSelectionScreen.route){
            HomeScreen(modifier, navController)
        }

        composable(route = AppNavigationItems.YearSelectionScreen.route+"/{id}"){
            val id = it.arguments?.getString("id")?:""
            YearSelectScreen(modifier = modifier, id, navController)
        }

        composable(route = AppNavigationItems.SubjectSelectionScreen.route+"/{path}"){
            val path = it.arguments?.getString("path")?:""
            SubjectSelectScreen(modifier = modifier, path, navController)
        }

        composable(route = AppNavigationItems.NotesSelectionScreen.route+"/{id}"){
            val id = it.arguments?.getString("id")?:""
            NotesScreen(modifier= modifier, id = id)
        }
    }
}