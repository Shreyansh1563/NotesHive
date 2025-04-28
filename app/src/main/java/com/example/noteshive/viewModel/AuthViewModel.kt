package com.example.noteshive.viewModel

import androidx.lifecycle.ViewModel
import com.example.noteshive.presentationIDs.AuthState
import com.example.noteshive.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel: ViewModel() {
    private val auth  = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.UnAuthenticated)
    val authState = _authState

    fun getUser(): FirebaseUser? {
        UserRepository.setUser(auth.currentUser)
        return auth.currentUser
    }

    fun checkAuthStatus(){
        if(getUser() == null){
            _authState.value = AuthState.UnAuthenticated
        } else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = AuthState.Authenticated
                getUser()
            }.addOnFailureListener {
                _authState.value = AuthState.Error(it.message?:"Something Went Wrong")
            }
    }

    fun signup(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = AuthState.Authenticated
                getUser()
            }.addOnFailureListener {
                _authState.value = AuthState.Error(it.message?:"Something Went Wrong")
            }
    }

    fun sighOut(){
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
    }


}