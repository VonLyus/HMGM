package com.healthcare.hmgm.data

data class Message(
    val id : Int,   //Message 마다 고유 id
    val name : String,
    val profile : String,
    val message : String,
    val timestamp : String,
)