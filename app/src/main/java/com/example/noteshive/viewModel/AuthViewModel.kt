package com.example.noteshive.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel: ViewModel() {
    private val auth  = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.UnAuthenticated)
    val authState = _authState

    fun checkAuthStatus(){
        if(auth.currentUser == null){
            _authState.value = AuthState.UnAuthenticated
        } else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return;
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = AuthState.Authenticated
            }.addOnFailureListener {
                _authState.value = AuthState.Error(it.message?:"Something Went Wrong")
            }
    }

    fun signup(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return;
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = AuthState.Authenticated
            }.addOnFailureListener {
                _authState.value = AuthState.Error(it.message?:"Something Went Wrong")
            }
    }

    fun sighOut(){
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
    }
}

sealed class AuthState {
    object Authenticated : AuthState()
    object UnAuthenticated: AuthState()
    object Loading: AuthState()
    data class Error(val message: String): AuthState()
}