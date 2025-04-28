package com.example.noteshive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.noteshive.navigation.AppNavigation
import com.example.noteshive.navigation.AppNavigationItems
import com.example.noteshive.ui.theme.NotesHiveTheme
import com.example.noteshive.viewModel.AuthViewModel
import com.example.noteshive.viewModel.OptionsViewModel
import com.example.noteshive.viewModel.UploadScreenViewModel

class MainActivity : ComponentActivity() {

    val viewModelOption by viewModels<OptionsViewModel>()
    val viewModelUpload by viewModels<UploadScreenViewModel>()
    val viewModelAuth by viewModels<AuthViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            NotesHiveTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {Text("NotesHive")},
                            actions = {
                                IconButton(
                                    onClick = {
                                        viewModelAuth.sighOut()
                                        navController.navigate(AppNavigationItems.LoginScreen.route) {
                                            popUpTo(0)
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.AccountCircle, contentDescription = "log out")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        viewModelOption = viewModelOption,
                        viewModelUpload = viewModelUpload,
                        viewModelAuth = viewModelAuth
                    )

//                    HomeScreen(modifier = Modifier.padding(innerPadding), navController)
//                    UploadScreen(Modifier.padding(innerPadding))
//                    NotesScreen(Modifier.padding(innerPadding), viewModelOption, navController)
//                    UploadScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
