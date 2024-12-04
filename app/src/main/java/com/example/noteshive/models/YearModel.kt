package com.example.noteshive.models


data class YearModel(
    val id: String = "",
    val name: String = "",
    val subjectsId: List<String> = emptyList()
)
