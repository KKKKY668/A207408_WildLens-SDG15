package com.example.a207408_cikguizwan_lab02

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//  Data Class
data class UserProfile(
    val name: String = "",
    val location: String = "Nearby",
)


class WildLensViewModel : ViewModel() {
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    fun updateProfile(name: String, location: String = "Nearby") {
        _userProfile.value = UserProfile(name = name, location = location )
    }
}