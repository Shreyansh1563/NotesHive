package com.example.noteshive.repository

import com.google.firebase.auth.FirebaseUser

object UserRepository{
    private var user: FirebaseUser? = null

    fun getUser(): FirebaseUser?{
        return user
    }

    fun setUser(u: FirebaseUser?){
        user = u
    }
}