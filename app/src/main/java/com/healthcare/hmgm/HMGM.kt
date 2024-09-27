package com.healthcare.hmgm

import android.app.Application
import com.healthcare.hmgm.data.InventoryDatabase
import com.healthcare.hmgm.data.OfflineItemsRepository
import com.healthcare.hmgm.data.UserInfoRepository

class app: Application() {
    companion object {
//        lateinit var pref: PreferenceUtil
    }
//    override val UserInfoRepository: UserInfoRepository by lazy {
//        OfflineItemsRepository(InventoryDatabase.getDatabase(this).userInfoDao())
//    }
}