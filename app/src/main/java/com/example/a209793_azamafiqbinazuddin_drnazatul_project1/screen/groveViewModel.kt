package com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen

import androidx.lifecycle.ViewModel
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.data.StudyBoard
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.data.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GroveViewModel : ViewModel() {

    //ViewModel for homescreen.kt
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    fun updateProfile(newName: String, newBio: String) {
        _userProfile.value = UserProfile(username = newName, bio = newBio)
    }

    //ViewModel for addBoardScree.kt / board list
    private val _boards = MutableStateFlow<List<StudyBoard>>(emptyList())
    val boards: StateFlow<List<StudyBoard>> = _boards.asStateFlow()

    fun addBoard(title: String, subject: String, goal: String) {
        val newBoard = StudyBoard(
            id = _boards.value.size + 1,
            title = title,
            subject = subject,
            goal = goal
        )
        _boards.value += newBoard //update every new board on the list
    }
}
