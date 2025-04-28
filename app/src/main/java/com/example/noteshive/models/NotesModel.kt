package com.example.noteshive.models

import java.net.URI

data class NotesModel(
    val id: String = "",
    val title: String = "",
    val downloadUrl: String = "",
    val upVote: Int = 0,
    val downVote: Int = 0,
    val ownerName: String = "",
    val imageUri: URI? = null,
    val userVotes: Map<String, VoteType> = emptyMap()
)