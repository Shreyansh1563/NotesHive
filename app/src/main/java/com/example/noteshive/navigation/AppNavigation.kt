package com.example.noteshive.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noteshive.screens.HomeScreen
import com.example.noteshive.screens.LoginScreen
import com.example.noteshive.screens.NotesScreen
import com.example.noteshive.screens.SignupScreen
import com.example.noteshive.screens.SubjectSelectScreen
import com.example.noteshive.screens.UploadScreen
import com.example.noteshive.screens.YearSelectScreen
import com.example.noteshive.viewModel.AuthViewModel
import com.example.noteshive.viewModel.OptionsViewModel
import com.example.noteshive.viewModel.UploadScreenViewModel

@Composable
fun AppNavigation(modifier: Modifier= Modifier, navController: NavHostController, viewModelOption: OptionsViewModel, viewModelUpload: UploadScreenViewModel, viewModelAuth: AuthViewModel){
    NavHost(navController = navController, startDestination = AppNavigationItems.LoginScreen.route){

        composable(route = AppNavigationItems.BranchSelectionScreen.route){
            HomeScreen(modifier, navController, viewModelOption)
        }

        composable(route = AppNavigationItems.YearSelectionScreen.route){
            YearSelectScreen(modifier = modifier, navController, viewModelOption)
        }

        composable(route = AppNavigationItems.SubjectSelectionScreen.route){
            SubjectSelectScreen(modifier = modifier, navController, viewModelOption)
        }

        composable(route = AppNavigationItems.NotesSelectionScreen.route){
            NotesScreen(modifier= modifier, viewModelOption, navController)
        }

        composable(route = AppNavigationItems.UploadScreen.route + "/{subjectCode}"){
            val subjectCode = it.arguments?.getString("subjectCode")?:""
            UploadScreen(modifier = modifier, subjectCode, viewModelUpload)
        }

        composable(route = AppNavigationItems.LoginScreen.route){
            LoginScreen(modifier = modifier, navController, viewModelAuth)
        }

        composable(route = AppNavigationItems.SighUpScreen.route){
            SignupScreen(modifier = modifier, viewModelAuth, navController)
        }
    }
}