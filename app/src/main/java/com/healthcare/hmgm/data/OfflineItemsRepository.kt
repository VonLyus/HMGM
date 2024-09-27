package com.healthcare.hmgm.data

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val UserInfoDao: UserInfoDao) : UserInfoRepository{
    override fun getAllUserInfoStream(): Flow<List<UserInfo>> = UserInfoDao.getAllUserInfo()

    override fun getUserInfoStream(id: Int): Flow<UserInfo?>  = UserInfoDao.getUserInfo(id)

    override suspend fun insertUserInfo(userInfo: UserInfo) = UserInfoDao.insert(userInfo)

    override suspend fun deleteUserInfo(userInfo: UserInfo) = UserInfoDao.delete(userInfo)

    override suspend fun updateUserInfo(userInfo: UserInfo) = UserInfoDao.update(userInfo)
}