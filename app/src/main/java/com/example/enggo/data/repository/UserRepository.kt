package com.example.enggo.data.repository

import com.example.enggo.model.ProfileData
import com.example.enggo.model.UserData


interface UserRepository {
    suspend fun addUserData(userData: UserData): String?
    suspend fun getUserDataById(userId: String): UserData?
    suspend fun getUserDataByUsername(userName : String): String?
    suspend fun updateUserData(userId: String, userData: UserData)
    suspend fun deleteUserDataById(userId: String)
    suspend fun checkUsernameAvailability(userName: String): Boolean
    suspend fun updateUserProfile(userId: String, profileData: ProfileData)
    suspend fun checkEmailAvailability(email: String): Boolean
    suspend fun verifyLoginInfo(userName: String, password: String): Boolean
    suspend fun getUserIdByUsername(userName: String): String?
    suspend fun getUserProfile(userId: String): ProfileData?
    suspend fun getUserAvatarFromUserId(userId: String): String?
    suspend fun getUserAvatarFromUsername(username: String): String?
    fun addFCMToken(token: String, userId: String)
    suspend fun getFCMToken(userId: String): String
    suspend fun getUsernameById(userId: String): String?

}