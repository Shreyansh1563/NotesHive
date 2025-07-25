package com.example.noteshive.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.noteshive.models.YearModel
import com.example.noteshive.navigation.AppNavigationItems
import com.example.noteshive.viewModel.OptionsViewModel

@Composable
fun YearSelectScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: OptionsViewModel) {

    val data = viewModel.yearData

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Select Year",
            style = TextStyle(
//                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(data){
                ListObjectsYears(it) {
                    viewModel.yearSelected(it)
                    navController.navigate(AppNavigationItems.SubjectSelectionScreen.route)
                }
            }
        }
    }
}

@Composable
fun ListObjectsYears(data: YearModel, onClick: ()->Unit){
    Card(
        onClick = {onClick()}
    ){
        Box(
            modifier = Modifier.fillMaxSize()
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
