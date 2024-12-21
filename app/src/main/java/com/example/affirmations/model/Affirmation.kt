package com.example.affirmations.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Affirmation(
    @StringRes val stringResourceId: Int,         //標題
    @DrawableRes val imageResourceId: Int,        //照片
    val commentId: String
)
