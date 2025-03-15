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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.noteshive.models.BranchModel
import com.example.noteshive.navigation.AppNavigationItems
import com.example.noteshive.viewModel.OptionsViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController,  viewModel: OptionsViewModel = viewModel()) {

    val data = viewModel.branchData
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Select Branch",
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
                    viewModel.branchSelected(it)
                    navController.navigate(AppNavigationItems.YearSelectionScreen.route)
                }
            }
        }
    }
}

@Composable
fun ListObjectsOthers(data: BranchModel, onClick: ()->Unit){
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