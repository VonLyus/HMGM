package com.healthcare.hmgm.ui.chatting

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.healthcare.hmgm.data.Message

class ChattingViewModel : ViewModel(){
    private val _messageList = mutableStateListOf<Message>()
    val messageList: List<Message> = _messageList
}