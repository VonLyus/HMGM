package com.healthcare.hmgm.data

data class Message(
    val id : Int,                   //  Message id
    val userId : Int,               //  유저 id
    val userName : String,          //  유저 이름
    val profile : String,
    val message : String,
    val timestamp : String,
){
    fun initMessage(userInfo: UserInfo, message: String, timestamp: String): Message{
        return Message(
            id = this.id,
            userId = userInfo.id,
            userName = userInfo.name,
            profile = userInfo.profile,
            message = message,
            timestamp = timestamp)
    }
}