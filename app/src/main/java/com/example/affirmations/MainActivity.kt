package com.example.affirmations

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.affirmations.data.Datasource
import com.example.affirmations.model.Postcard
import com.example.affirmations.ui.theme.AffirmationsTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme {
                PostcardApp()
            }
        }
    }
}


@Composable
fun PostcardApp() {
    var selectedPostcard by remember { mutableStateOf<Postcard?>(null) }
    var showEnlargedPhoto by remember { mutableStateOf(false) }
    var showGreeting by remember { mutableStateOf(false) }

    AffirmationsTheme {
        Scaffold(
            content = { paddingValues ->
                when {
                    showGreeting && selectedPostcard != null -> {
                        selectedPostcard?.let { postcard ->
                            Greeting(postcard) {
                                showGreeting = false
                                showEnlargedPhoto = true
                            }
                        }
                    }
                    showEnlargedPhoto && selectedPostcard != null -> {
                        selectedPostcard?.let { postcard ->
                            PhotoViewer(postcard) {
                                showEnlargedPhoto = false
                                showGreeting = false
                                selectedPostcard = null
                            }
                        }
                    }
                    else -> {
                        PostcardList(
                            postcardList = Datasource().loadPostcards(),
                            onClickPostcard = { postcard ->
                                selectedPostcard = postcard
                                showGreeting = true
                            },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun PostcardList(
    postcardList: List<Postcard>,
    onClickPostcard: (Postcard) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(postcardList) { postcard ->
            PostcardCard(
                postcard = postcard,
                onClick = { onClickPostcard(postcard) },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun PostcardCard(
    postcard: Postcard,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(postcard.imageUrl),
                contentDescription = postcard.comment,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = postcard.stringContent,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
private fun PostcardCardPreview() {
    PostcardCard(
        postcard = Postcard(
            stringContent = "Hawaii",
            sendDate = "2024/12/21",
            arrivedDate = "2025/01/01",
            comment = "This is a comment test01",
            imageUrl = ""
        ),
        onClick = {}
    )
}

@Composable
fun PhotoCard(
    postcard: Postcard,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(postcard.imageUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(1f),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
    }
}

@Composable
fun PhotoViewer(postcard: Postcard, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { onDismiss() }) {
            Image(
                painter = rememberAsyncImagePainter(postcard.imageUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

//comment view
@Composable
fun Greeting(
    postcard: Postcard,
    onDismiss: () -> Unit
) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onDismiss() }) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = postcard.comment,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentPreview() {
    AffirmationsTheme {
        Greeting(
            postcard = Postcard(
                stringContent = "Hawaii",
                sendDate = "2024/12/21",
                arrivedDate = "2025/01/01",
                comment = "This is a comment test01",
                imageUrl = ""
            )
        ) {}
    }
}

@Preview
@Composable
fun PhotoPreview() {
    PhotoCard(
        postcard = Postcard(
            stringContent = "Hawaii",
            sendDate = "2024/12/21",
            arrivedDate = "2025/01/01",
            comment = "This is a comment test01",
            imageUrl = "https://pbs.twimg.com/media/GbOTK6kakAMU_yc?format=jpg&name=medium"
        ),
        onClick = {}
    )
}

/******/
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SendList(
    postcardList: List<Postcard>,
    onClickPostcard: (Postcard) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(postcardList) { postcard ->
            val status = calculateStatus(postcard.arrivedDate)
            val backgroundColor = when (status) {
                "寄送中" -> Color(0xFFE0F7FA) // 淺藍色
                "已送達" -> Color(0xFFE8F5E9) // 淺綠色
                else -> Color(0xFFF5F5F5)    // 默認淺灰色
            }
            val textColor = when (status) {
                "寄送中" -> Color(0xFF01579B) // 深藍色
                "已送達" -> Color(0xFF2E7D32) // 深綠色
                else -> Color(0xFF757575)    // 默認灰色
            }

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable(enabled = status == "已送達") {
                        onClickPostcard(postcard)
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = status,
                            style = MaterialTheme.typography.bodyMedium.copy(color = textColor),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "寄出時間",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "送達時間",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = postcard.stringContent,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = postcard.sendDate,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = postcard.arrivedDate,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateStatus(arrivedDate: String): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val currentDate = LocalDate.now()
        val arrivalDate = LocalDate.parse(arrivedDate, formatter)

        if (currentDate.isBefore(arrivalDate)) "寄送中" else "已送達"
    } catch (e: Exception) {
        "未知"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewSendList() {
    val samplePostcards = listOf(
        Postcard(
            stringContent = "Hawaii",
            sendDate = "2024/12/21",
            arrivedDate = "2025/01/01",
            comment = "This is a comment test01",
            imageUrl = ""
        ),
        Postcard(
            stringContent = "Seoul",
            sendDate = "2024/06/01",
            arrivedDate = "2024/12/21",
            comment = "This is a comment test02",
            imageUrl = ""
        ),

        Postcard(
            stringContent = "Tokyo",
            sendDate = "2024/06/01",
            arrivedDate = "2025/12/21",
            comment = "This is a comment test02",
            imageUrl = ""
        )
    )

    SendList(postcardList = samplePostcards, onClickPostcard = {
        println("Clicked postcard: ${it.stringContent}")
    })
}


