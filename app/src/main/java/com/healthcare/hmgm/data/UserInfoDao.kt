package com.healthcare.hmgm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: UserInfo)

    @Update
    suspend fun update(item: UserInfo)

    @Delete
    suspend fun delete(item: UserInfo)

    @Query("SELECT * from UserInfo WHERE id = :id")
    fun getUserInfo(id: Int): Flow<UserInfo>

    @Query("SELECT * from UserInfo ORDER BY name ASC")
    fun getAllUserInfo(): Flow<List<UserInfo>>
}