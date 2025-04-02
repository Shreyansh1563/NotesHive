package com.example.noteshive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.compose.rememberNavController
import com.example.noteshive.navigation.AppNavigation
import com.example.noteshive.screens.NotesScreen
import com.example.noteshive.screens.UploadScreen
import com.example.noteshive.ui.theme.NotesHiveTheme
import com.example.noteshive.viewModel.OptionsViewModel
import com.example.noteshive.viewModel.UploadScreenViewModel

class MainActivity : ComponentActivity() {

    val viewModelOption by viewModels<OptionsViewModel>()
    val viewModelUpload by viewModels<UploadScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            NotesHiveTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding), navController = navController, viewModelOption = viewModelOption, viewModelUpload = viewModelUpload)
//                    HomeScreen(modifier = Modifier.padding(innerPadding), navController)
//                    UploadScreen(Modifier.padding(innerPadding))
//                    NotesScreen(Modifier.padding(innerPadding), viewModelOption, navController)
//                    UploadScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
