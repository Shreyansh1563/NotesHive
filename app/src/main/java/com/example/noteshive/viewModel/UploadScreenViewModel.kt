package com.example.noteshive.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.noteshive.models.NotesModel
import com.example.noteshive.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID


class UploadScreenViewModel : ViewModel(){
    private val _isUploading = MutableStateFlow<Boolean>(false)
    private lateinit var downloadUrl: String
    private lateinit var thumbnailUrl: String
    private val _uri = MutableStateFlow<Uri?>(null)
    private val storageReference  = FirebaseStorage.getInstance().reference
    private val _db = FirebaseFirestore.getInstance()

    val uri : StateFlow<Uri?> = _uri
    val isUploading: StateFlow<Boolean> = _isUploading
    var title = MutableStateFlow("")
    var subjectCode  = ""


    fun setUri(newUri: Uri?){
        _uri.value = newUri
    }

    fun setTitle(newTitle: String){
        title.value = newTitle
    }


    fun uploadPdf(thumbnailUri: Uri?){
        val name = generateUniquePdfName()
        val pdfReference = storageReference.child("/NotesPdf/$name")
        val thumbnailReference = storageReference.child("/thumbnails/$name")
        if(_uri.value != null && !title.value.isEmpty() && subjectCode != "" && thumbnailUri != null) {
            _isUploading.value = true
            val uploadTask = pdfReference.putFile(_uri.value!!)
            uploadTask.addOnSuccessListener{
                pdfReference.downloadUrl.addOnSuccessListener { url ->
                    downloadUrl = url.toString()
                    thumbnailReference.putFile(thumbnailUri).addOnSuccessListener {
                        thumbnailReference.downloadUrl.addOnSuccessListener {
                            thumbnailUrl = it.toString()
                            addUploadOnFireStore()
                            _isUploading.value = false
                            Log.d("mine", "data uploaded $downloadUrl")
                        }
                    }

                }
            }
                .addOnFailureListener{
                    _isUploading.value = false
                    Log.d("mine", "uploading failed")
                }
        }
        else {
            Log.d("mine", "fill every entry")
        }
    }

    private fun addUploadOnFireStore(){
        val newDocRef =  _db.collection("/subjects/$subjectCode/notes").document()
        val user = UserRepository.getUser()
        val data = NotesModel(
            id = newDocRef.id,
            title = title.value,
            downloadUrl = downloadUrl,
            thumbnailUrl = thumbnailUrl,
            ownerName = user!!.displayName.toString(),
            imageUrl = user.photoUrl.toString()
        )
        newDocRef.set(data)
            .addOnSuccessListener{Log.d("mine", "Data added on FireStore")}
            .addOnFailureListener{Log.d("mine", "Data upload failed on FireStore")}
    }

    private fun generateUniquePdfName(): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val uuid = UUID.randomUUID().toString().replace("-", "")
        return "pdf_${timestamp}_${uuid}"
    }
}