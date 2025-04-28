package com.example.noteshive.presentationIDs

sealed class AuthState {
    object Authenticated : AuthState()
    object UnAuthenticated: AuthState()
    data class Error(val message: String): AuthState()
}