package com.example.noteshive.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.noteshive.R
import com.example.noteshive.models.SubjectModel
import com.example.noteshive.navigation.AppNavigationItems
import com.example.noteshive.viewModel.OptionsViewModel

@Composable
fun SubjectSelectScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: OptionsViewModel) {

    val data = viewModel.subjectData


    Column(
        modifier = modifier
            .fillMaxSize()
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
                    viewModel.subjectSelected(it)
                    navController.navigate(AppNavigationItems.NotesSelectionScreen.route)
                }
            }
        }
    }
}

@Composable
fun ListObjectsSubject(data: SubjectModel, onClick: ()->Unit){
    Card(
        modifier = Modifier.padding(18.dp),
        onClick = {onClick()}
    ){
        Box(
            modifier = Modifier.fillMaxWidth().height(150.dp)
        ){
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().background(Color.Black).graphicsLayer(alpha = .85f, scaleX = -1f)
            )
            Text(
                text = data.name,
                fontSize = 38.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_forward_24),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart).size(60.dp).padding(10.dp)
            )
            Image(
                painter = painterResource(R.drawable.bee_logo),
                contentDescription = "",
                modifier = Modifier.align(Alignment.BottomEnd).size(90.dp).graphicsLayer(alpha = .9f)
            )
        }
    }
}