package com.example.noteshive.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.noteshive.R
import com.example.noteshive.models.NotesModel
import com.example.noteshive.models.VoteType
import com.example.noteshive.navigation.AppNavigationItems
import com.example.noteshive.repository.UserRepository
import com.example.noteshive.viewModel.OptionsViewModel

@Composable
fun NotesScreen(modifier: Modifier = Modifier, viewModel: OptionsViewModel, navController: NavHostController){

    val context = LocalContext.current
    val data = viewModel.notesData
    val user = UserRepository.getUser()

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
                    NotesCard(
                        note = it,
                        vote = it.userVotes.getOrDefault(user!!.uid, VoteType.NOVOTE),
                        onUpvote = {viewModel.upVote(it)},
                        onDownVote = {viewModel.downVote(it)},
                        onClick = {viewModel.openPdfWithIntent(context, it.downloadUrl)}
                    )
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
fun NotesCard(
    note: NotesModel,
    vote: VoteType,
    onUpvote: (NotesModel) -> Unit,
    onDownVote: (NotesModel) -> Unit,
    onClick: ()->Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        onClick = { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AsyncImage(
                    model = note.imageUrl,
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .height(30.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = note.ownerName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            if(note.thumbnailUrl.isEmpty()) {
                Image(
                    painter = painterResource(R.drawable.background),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
            }
            else{
                AsyncImage(
                    model = note.thumbnailUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { onUpvote(note) },
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(if (vote == VoteType.UPVOTE) R.drawable.up_arrow else R.drawable.up_arrow_empty),
                        contentDescription = "UpVote",
                        tint = Color.Unspecified
                    )
                }
                Text(
                    text = note.upVote.toString(),
                    fontSize = 20.sp,
                )
                Spacer(Modifier.width(20.dp))
                IconButton(
                    onClick = { onDownVote(note) },
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp).rotate(180f),
                        painter = painterResource(if (vote == VoteType.DOWNVOTE) R.drawable.up_arrow else R.drawable.up_arrow_empty),
                        contentDescription = "downVote",
                        tint = Color.Unspecified
                    )
                }
                Text(
                    text = note.downVote.toString(),
                    fontSize = 20.sp,
                )
            }
            Text(text = note.title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}