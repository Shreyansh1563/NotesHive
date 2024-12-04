package com.example.noteshive.models

data class BranchModel(
    val id: String = "",
    val name: String = "",
    val years: List<YearModel> = emptyList<YearModel>()
)
