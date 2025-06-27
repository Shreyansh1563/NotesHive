package com.example.noteshive.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteshive.navigation.AppNavigationItems
import com.example.noteshive.viewModel.AuthViewModel
import com.example.noteshive.R
import com.example.noteshive.presentationIDs.AuthState

//@Preview (showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: AuthViewModel){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = viewModel.authState.collectAsState()
    val loggingIn = viewModel.loggingIn
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.checkAuthStatus()
    }
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(AppNavigationItems.BranchSelectionScreen.route){popUpTo(0)}
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.bee_logo),
            contentDescription = "honey bee logo"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Login Page",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("Email")},
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "email icon") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text("Password")},
            leadingIcon = {Icon(Icons.Default.Lock, contentDescription = "lock icon")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.login(email, password)
            },
            enabled = !loggingIn
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = {
                navController.navigate(AppNavigationItems.SighUpScreen.route)
            }
        ) {
            Text("Don't have an account, SignUp")
        }
    }
}
