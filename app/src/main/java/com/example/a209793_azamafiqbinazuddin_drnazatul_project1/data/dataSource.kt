package com.example.a209793_azamafiqbinazuddin_drnazatul_project1.data

data class UserProfile(
    val username: String = "Grove Explorer",
    val bio: String = "Planting new ideas."
)

data class StudyBoard(
    val id: Int,
    val title: String,
    val subject: String,
    val goal: String
)