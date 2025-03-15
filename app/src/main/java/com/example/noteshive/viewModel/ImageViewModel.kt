package com.example.noteshive.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.noteshive.models.NotesModel
import com.google.firebase.firestore.FirebaseFirestore


class ImageViewModel : ViewModel() {

    val db = FirebaseFirestore.getInstance()
    var imageUrlState = mutableStateOf<String?>(null)
        private set

//    fun fetchImageUrl(documentId: String) {
//        firestore.collection("images").document(documentId)
//            .get()
//            .addOnSuccessListener { document ->
//                if (document.exists()) {
//                    val imageUrl = document.getString("imageUrl") // Field name in Firestore
//                    imageUrlState.value = imageUrl
//                }
//            }
//            .addOnFailureListener { e ->
//                Log.e("FireStore", "Error fetching image", e)
//            }
//    }

    fun fetchImageUrl(documentId: String){

    }
}
