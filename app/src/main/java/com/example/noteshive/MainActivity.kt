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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.noteshive.navigation.AppNavigation
import com.example.noteshive.navigation.AppNavigationItems
import com.example.noteshive.presentationIDs.AuthState
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
            var expanded by remember { mutableStateOf(false) }

            NotesHiveTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {Text("NotesHive")},
                            actions = {
                                IconButton(
                                    onClick = {
                                        if(viewModelAuth.authState.value is AuthState.Authenticated) {
                                            expanded = true
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.AccountCircle, contentDescription = "log out")
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("logout") },
                                            onClick = {
                                                expanded = false
                                                viewModelAuth.sighOut()
                                                navController.navigate(AppNavigationItems.LoginScreen.route) {
                                                    popUpTo(0)
                                                }
                                            }
                                        )
                                    }
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
