package com.example.noteshive.screens

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.noteshive.models.SubjectModel
import com.example.noteshive.navigation.AppNavigationItems
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SubjectSelectScreen(modifier: Modifier = Modifier, id: String, navController: NavHostController) {

    val ids = id.split("|")

    val data = remember { mutableStateListOf<SubjectModel>()}

    val db = FirebaseFirestore.getInstance()
    val subjectsCollection = db.collection("subjects").whereIn("id", ids)

    LaunchedEffect(Unit) {

        subjectsCollection.addSnapshotListener{value, error->
            if(error == null){
                val dbData = value?.toObjects(SubjectModel:: class.java)
                data.addAll(dbData!!)
            }
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Select Subject",
            style = TextStyle(
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn {
            items(data){
                ListObjectsSubject(it) {
                    navController.navigate(AppNavigationItems.NotesSelectionScreen.route + "/${it.id}")
                }
            }
        }
    }
}

@Composable
fun ListObjectsSubject(data: SubjectModel, onClick: ()->Unit){
    Card(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        ),
        onClick = {onClick()}
    ){
        Text(
            modifier = Modifier.padding(20.dp),
            text = data.name,
            style = TextStyle(
                color = Color.LightGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}