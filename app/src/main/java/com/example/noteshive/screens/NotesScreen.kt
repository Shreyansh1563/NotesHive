package com.example.noteshive.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteshive.models.NotesModel
import com.example.noteshive.viewModel.OptionsViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NotesScreen(modifier: Modifier = Modifier, id: String, navController: NavController, viewModel: OptionsViewModel){

    val context = LocalContext.current

//    val data = remember { mutableStateListOf<NotesModel>()}
//
//    val db = FirebaseFirestore.getInstance()
//    val branchCollection = db.collection("subjects").document(id).collection("notes")
//
//    LaunchedEffect(Unit) {
//        Log.d("id", id)
//        branchCollection.addSnapshotListener{value, error->
//            if(error == null){
//                val dbData = value?.toObjects(NotesModel:: class.java)
//                data.addAll(dbData!!)
//            }
//        }
//    }

    val data = viewModel.notesData


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Select Notes",
            style = TextStyle(
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn {
            items(data){
                ListObjectsOthers(it) {
                    Log.d(it.title, it.downloadUrl)
                    viewModel.openPdfWithIntent(context, it.downloadUrl)
                }
            }
        }
    }
}

@Composable
fun ListObjectsOthers(data: NotesModel, onClick: ()->Unit){
    Card(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        ),
        onClick = {onClick()}
    ){
        Text(
            modifier = Modifier.padding(20.dp),
            text = data.title,
            style = TextStyle(
                color = Color.LightGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}