package com.healthcare.hmgm.ui.chatting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.healthcare.hmgm.R
import com.healthcare.hmgm.data.Message
import com.healthcare.hmgm.data.UserInfo
import com.healthcare.hmgm.ui.theme.HMGMTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class ChattingActivity : ComponentActivity() {
    companion object {
        /*   Activity 두가지 방식   */
        fun newIntent(context: Context): Intent =
            Intent(context, ChattingActivity::class.java).apply {

            }
//        fun newIntent(context: Context): Intent = Intent().setComponent(
//            ComponentName(
//                "com.healthcare.hmgm",
//                "com.healthcare.hmgm.ui.chatting.ChattingActivity"
//            )
//        )
    }

    private val viewModel: ChattingViewModel by viewModels {
        ChattingViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {                            //UI 설정
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
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }
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
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* 액션 버튼 클릭 시 동작 */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Bottom sheet를 다시 표시하기
                    showBottomSheet = true
                }, shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 10.dp)
            )
            {
                Icon(Icons.Filled.Add, contentDescription = "Show Bottom Sheet")
            }
        }
    ) { innerPadding ->             // 화면 컨텐츠
        recyclerViewContent()
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                modifier = Modifier.fillMaxWidth(),
                sheetState = sheetState,
            ) {
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                    Text("hide sheet")
                }
                recyclerViewContent()
            }
        } else {
            // Bottom sheet가 보이지 않을 때도 다른 내용이 표시되도록 할 수 있음
            Text(
                "Bottom sheet is hidden", modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun recyclerViewContent() {//, users: List<UserInfo>
    val userInfo = UserInfo()
    val message = Message(
        id = 0,
        userId = 0,
        userName = "",
        profile = "",
        message = "",
        timestamp = ""
    )
    val initUserMake = message.initMessage(userInfo, "안녕하세요", getCurrentTime())
    val messageList = List(3) { initUserMake }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(
            messageList
        ) { index, item ->
            otherUserItemLayout(item)
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileImg(imgUrl: String) {
    // 이미지 비트맵
    val bitmap: MutableState<Bitmap?> = mutableStateOf(null)

    val imageModifier = Modifier
        .size(width = 50.dp, height = 50.dp)
        .clip(CircleShape)

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imgUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
            ) {
                bitmap.value = resource
            }
            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })

    // bitmap에 데이터가 있다면? -> 이미지를 다운 받았다면
    bitmap.value?.asImageBitmap()?.let {
        Image(
            bitmap = it,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = imageModifier
        )
    } ?: Image(
        painter = painterResource(id = R.drawable.defaultimg) // 다운 받은 이미지가 없는 경우
        , contentScale = ContentScale.Fit, contentDescription = null, modifier = imageModifier
    )
}

@Composable
fun userItemLayout(message: Message) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(5.dp)
            .background(colorResource(id = R.color.transparent)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        ProfileImg(message.profile)
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message.userName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .padding(3.dp)
                    .wrapContentWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ){
                Text(
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 5.dp, 0.dp),
                    text = message.message,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Thin
                )
            }
        }
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = message.timestamp,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,)
        }
    }
}
@Composable
fun otherUserItemLayout(message: Message) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(5.dp)
            .background(colorResource(id = R.color.transparent)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        ProfileImg(message.profile)
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message.userName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .padding(3.dp)
                    .wrapContentWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ){
                Text(
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 5.dp, 0.dp),
                    text = message.message,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Thin
                )
            }
        }
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = message.timestamp,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,)
        }
    }
}

fun getCurrentTime(): String {
    val formatter = "yyyy-MM-dd HH:mm:ss"
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val dateTimeFormatter = DateTimeFormatter.ofPattern(formatter)
        current.format(dateTimeFormatter)
    } else {
        val current = Date()
        val simpleDateFormat = SimpleDateFormat(formatter, Locale.getDefault())
        simpleDateFormat.format(current)
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFFFFFF, widthDp = 200, heightDp = 100)
//@Composable
//fun PreviewGreeting() {
//    initLayout()
//}