package com.example.affirmations.model

data class Postcard(
    val stringContent: String,       // 使用者輸入的標題
    val imageUrl: String,            // 使用者提供的圖片 URL 或路徑
    val comment: String,             // 使用者輸入的內文
    val arrivedDate: String,         // 使用者輸入的日期
    val sendDate: String                // 當天日期
)
