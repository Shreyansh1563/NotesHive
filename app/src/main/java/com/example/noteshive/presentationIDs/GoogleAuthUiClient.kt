package com.example.noteshive.presentationIDs

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.noteshive.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await


class GoogleAuthUiClient(
    private val context: Context
) {
    private val tag =  "mine"
    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun isSignedIn(): Boolean{
        return firebaseAuth.currentUser != null
    }

    suspend fun signInWithGoogle(): Boolean{

        if(isSignedIn()){
            return true
        }

        try{
            val result = buildCredentialRequest()
            return handelSignIn(result)
        }catch(e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            Log.e(tag, "signIn error: ${e.message}")
            return false
        }
    }

    private suspend fun handelSignIn(result: GetCredentialResponse): Boolean{
        val credential = result.credential

        if(credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
            try{
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                Log.d(tag, "name: ${tokenCredential.displayName}")
                Log.d(tag, "email: ${tokenCredential.id}")
                Log.d(tag, "profile: ${tokenCredential.profilePictureUri}")

                val authCredential = GoogleAuthProvider.getCredential(tokenCredential.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                return authResult.user != null

            }catch (e: GoogleIdTokenParsingException){
                Log.e(tag, "GoogleIdTokenParsingException: ${e.message}")
                return false

            }
        }else{
            Log.d(tag, "credential is not GoogleIdTokenCredential")
            return false
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse{
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.FireBaseAuthClientId))
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()
        return credentialManager.getCredential(request = request, context = context)
    }

    suspend fun signOut(){
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        firebaseAuth.signOut()
    }

}