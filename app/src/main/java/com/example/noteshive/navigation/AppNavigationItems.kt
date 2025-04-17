package com.example.noteshive.navigation

sealed class AppNavigationItems(val route: String) {
    object BranchSelectionScreen: AppNavigationItems(route = "branchSelection")
    object YearSelectionScreen: AppNavigationItems(route = "yearSelection")
    object SubjectSelectionScreen: AppNavigationItems(route = "subjectSelection")
    object NotesSelectionScreen: AppNavigationItems(route= "notesSelection")
    object UploadScreen: AppNavigationItems(route = "uploadScreen")
    object LoginScreen: AppNavigationItems(route = "loginScreen")
    object SighUpScreen: AppNavigationItems(route = "sighupScreen")
}