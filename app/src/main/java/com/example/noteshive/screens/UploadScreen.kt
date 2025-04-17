package com.example.noteshive.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteshive.R
import com.example.noteshive.viewModel.UploadScreenViewModel

@Composable
fun UploadScreen(modifier: Modifier = Modifier, subjectCode: String, viewModel: UploadScreenViewModel = viewModel()) { //, viewModel: UploadScreenViewModel = viewModel()) {
    val title by viewModel.title.collectAsState()
    val uri by viewModel.uri.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.setUri(it)
    }
    viewModel.subjectCode = subjectCode
//    Log.d("mine", subjectCode)

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f)
                .background(color = Color.Black)
        ) {
            Image(
                painter = painterResource(R.drawable.honeycomb_background),
                contentDescription = "background image",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize().graphicsLayer(alpha = .85f)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_354352),
                    contentDescription = "file logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.5f)
                        .padding(top = 60.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    text = uri?.toString()?: "No File Selected...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(.55f))
            Button(
                onClick = {launcher.launch("application/pdf")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.yellow)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 9.dp)
            ) {
                Text(
                    text = "Select Pdf",
                    fontSize = 35.sp,
                    modifier = Modifier.padding(10.dp),
                )
            }
            Spacer(Modifier.height(70.dp))
            OutlinedTextField(
                value = title,
                onValueChange = {viewModel.setTitle(it)},
                label = { Text("Notes Title", color = Color.Gray) },
                shape = RoundedCornerShape(8.dp),
            )
            if(isUploading){
                CircularProgressIndicator(modifier.height(30.dp), color = Color.White)
            }
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.yellow)),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            onClick = {viewModel.uploadPdf()}
        ) {
            Text("Upload Notes", fontSize = 30.sp)
        }
    }
}