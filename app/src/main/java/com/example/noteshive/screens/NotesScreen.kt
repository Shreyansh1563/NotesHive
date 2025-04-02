package com.example.noteshive.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.noteshive.models.NotesModel
import com.example.noteshive.navigation.AppNavigationItems
import com.example.noteshive.viewModel.OptionsViewModel

@Composable
fun NotesScreen(modifier: Modifier = Modifier, viewModel: OptionsViewModel, navController: NavHostController){

    val context = LocalContext.current
    val data = viewModel.notesData

    Box(modifier = modifier){
        Column(
            modifier = Modifier
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
                items(data) {
                    ListObjectsOthers(it) {
                        Log.d(it.title, it.downloadUrl)
                        viewModel.openPdfWithIntent(context, it.downloadUrl)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                Log.d("mine", "subject: ${viewModel.selectedSubject!!.id}")
                navController.navigate(AppNavigationItems.UploadScreen.route + "/${viewModel.selectedSubject!!.id}")
                      },
            modifier = Modifier.align(Alignment.BottomEnd).padding(30.dp).size(70.dp)
        ) {Icon(Icons.Default.Add, contentDescription = "Add") }
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