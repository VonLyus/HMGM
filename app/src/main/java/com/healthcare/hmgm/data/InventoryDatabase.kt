package com.healthcare.hmgm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase(){
    abstract fun userInfoDao(): UserInfoDao

    companion object {
        // 전체 앱에 인스턴스 하나만 있으면 되므로 RoomDatabase를 싱글톤으로 만든다.
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}