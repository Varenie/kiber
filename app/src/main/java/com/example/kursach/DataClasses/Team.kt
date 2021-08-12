package com.example.kursach.DataClasses

data class Team(
    val id: Int = 0,
    val name: String,
    val slogan: String,
    val wins: Int,
    val loses: Int,
    val draws: Int,
    val description: String = ""
)
