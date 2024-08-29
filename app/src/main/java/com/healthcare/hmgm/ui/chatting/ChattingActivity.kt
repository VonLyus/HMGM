package com.healthcare.hmgm.ui.chatting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.healthcare.hmgm.R
import com.healthcare.hmgm.ui.theme.HMGMTheme

class ChattingActivity : ComponentActivity() {
    companion object {
        /*   Activity 두가지 방식   */
        fun newIntent(context: Context): Intent = Intent(context, ChattingActivity::class.java).apply {

        }
//        fun newIntent(context: Context): Intent = Intent().setComponent(
//            ComponentName(
//                "com.healthcare.hmgm",
//                "com.healthcare.hmgm.ui.chatting.ChattingActivity"
//            )
//        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {        //UI 설정
            HMGMTheme {
                initLayout()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun initLayout() {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("User Name") },
                modifier = Modifier,
                navigationIcon = {              // 왼쪽 아이콘
                    IconButton(onClick = {
                        (context as Activity).finish()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* 액션 버튼 클릭 시 동작 */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { innerPadding ->             // 화면 컨텐츠
        recyclerViewContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
fun recyclerViewContent(modifier: Modifier){
    LazyColumn(
        modifier = modifier
    ){

    }
}

@Composable
fun itemLayout(modifier: Modifier){
    Surface(
        modifier = modifier,
        color = colorResource(id = R.color.message_background),
        shape = RoundedCornerShape(8.dp),
    ) {

    }
}
