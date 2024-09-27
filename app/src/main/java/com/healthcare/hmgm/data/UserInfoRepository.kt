package com.healthcare.hmgm.data

import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    fun getAllUserInfoStream(): Flow<List<UserInfo>>
    fun getUserInfoStream(id: Int): Flow<UserInfo?>
    suspend fun insertUserInfo(userInfo: UserInfo)
    suspend fun deleteUserInfo(userInfo: UserInfo)
    suspend fun updateUserInfo(userInfo: UserInfo)
}