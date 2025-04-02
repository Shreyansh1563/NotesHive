package com.example.noteshive.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        OutlinedTextField(
//            value = title,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            onValueChange = {viewModel.setTitle(it)},
//            label = {Text("Title")}
//        )
//        Spinner(
//            options = viewModel.subjectCodes,
//            selectedOption = viewModel.subjectCode,
//            onOptionSelected = {
//                viewModel.subjectCode = it
//                Log.d("mine", viewModel.subjectCode)
//            },
//            label = "SubjectCode"
//        )
//        Text(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            text = uri?.toString() ?: "No Data",
//            textAlign = TextAlign.Center
//        )
//        Button(
//            onClick = {
//                launcher.launch("application/pdf")
//            }
//        ) {
//            Text("Pick Pdf")
//        }
//        Button(
//            onClick = {
//                viewModel.uploadPdf()
//            }
//        ){
//            if(isUploading) CircularProgressIndicator(modifier.height(30.dp), color = Color.White)
//            else Text("Upload Pdf")
//        }
//    }
//}
//
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Spinner(
//    options: List<String>,
//    selectedOption: String,
//    onOptionSelected: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    label: String = ""
//) {
//    var expanded by remember { mutableStateOf(false) }
//    var currentSelectedItem by remember { mutableStateOf(selectedOption) }
//
//    Column(modifier = modifier) {
//        OutlinedTextField(
//            value = currentSelectedItem,
//            onValueChange = {},
//            readOnly = true,
//            label = { Text(label) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { expanded = true }, // Enable clicking on the text field
//            trailingIcon = { // Use trailing icon to show dropdown
//                Icon(
//                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
//                    contentDescription = "Dropdown Icon",
//                    modifier = Modifier.clickable { expanded = !expanded }
//                )
//            }
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            options.forEach { item ->
//                DropdownMenuItem(
//                    text = { Text(text = item, textAlign = TextAlign.Center) },
//                    onClick = {
//                        currentSelectedItem = item
//                        onOptionSelected(item)
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}


//@Preview(showBackground = true)
//@Composable
//private fun View() {
//    UploadScreen()
//}