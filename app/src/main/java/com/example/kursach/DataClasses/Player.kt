package com.example.kursach.DataClasses

data class Player(
    val id: Int = 0,
    val team_id: Int,
    val fullname: String,
    val nickname: String,
    val games: String,
    val description: String = ""
)
