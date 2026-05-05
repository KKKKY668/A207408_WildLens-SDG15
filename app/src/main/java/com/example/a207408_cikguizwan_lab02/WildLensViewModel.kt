package com.example.a207408_cikguizwan_lab02

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// 1. 将 ActivityLog 数据类放在这里统一管理
data class ActivityLog(
    val species: String,
    val location: String,
    val time: String,
    val imageRes: Int
)

data class UserProfile(
    val name: String = "",
    val location: String = "Nearby",
)

class WildLensViewModel : ViewModel() {
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // 2. 初始的列表数据（保留你原本漂亮的假数据作为打底）
    private val _activityLogs = MutableStateFlow<List<ActivityLog>>(
        listOf(
            ActivityLog("Hornbill", "Fraser's Hill", "Today, 8:02 AM", R.drawable.img_hornbill),
            ActivityLog("Rafflesia", "Cameron Highlands", "Yesterday, 3:45 PM", R.drawable.img_rafflesia),
            ActivityLog("Sun Bear", "Borneo Reserve", "Yesterday, 11:20 AM", R.drawable.img_bear),
            ActivityLog("Butterflies", "Penang Hill", "2 days ago, 2:30 PM", R.drawable.img_butterfly),
            ActivityLog("Little Heron", "Putrajaya Wetlands", "3 days ago, 7:10 AM", R.drawable.img_heron)
        )
    )
    val activityLogs: StateFlow<List<ActivityLog>> = _activityLogs.asStateFlow()

    fun updateProfile(name: String, location: String = "Nearby") {
        _userProfile.value = UserProfile(name = name, location = location )
    }

    // 3. 核心功能：添加新识别的数据到列表最前面
    fun addActivityLog(log: ActivityLog) {
        _activityLogs.value = listOf(log) + _activityLogs.value
    }
}