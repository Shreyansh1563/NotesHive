package com.example.noteshive.models

data class NotesModel(
    val id: String = "",
    val title: String = "",
    val downloadUrl: String = "",
    val upVote: Int = 0,
    val downVote: Int = 0,
    val ownerName: String = "",
    val imageUrl: String = "",
    val userVotes: Map<String, VoteType> = emptyMap(),
    val thumbnailUrl: String = ""
)