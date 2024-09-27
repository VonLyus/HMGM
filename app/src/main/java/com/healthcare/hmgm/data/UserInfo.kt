package com.healthcare.hmgm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Firebase에서 유저의 정보를 갖고와서 설정할 예정
@Entity(tableName = "UserInfo")
data class UserInfo (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,                   // 유저 Token 저장
    val name : String = "홍길동",
    val profile : String = "",
    val email : String = "",
    val age : Int = 0,
    val password : String = "",          // 유저 패스워드는 휘발성으로 설정해야 함
    val phoneNumber : String = "",
    val birth : String = "",
    val gender : String = "",
    val timestamp : String = "",         // 유저 아이디 생성시기
){
    fun initUserInfo(
        name : String,
        profile : String,
        email : String,
        age : Int,
        password : String,
        phoneNumber : String,
        birth : String,
        gender : String,
        timestamp : String,
    ) : UserInfo{
        return UserInfo(
            name = name,
            profile = profile,
            email = email,
            age = age,
            password = password,
            phoneNumber = phoneNumber,
            birth = birth,
            gender = gender,
            timestamp = timestamp,
        )
    }
}