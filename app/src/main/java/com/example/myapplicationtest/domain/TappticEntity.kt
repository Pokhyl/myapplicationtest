package com.example.myapplicationtest.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TappticEntity(
    val image: String,
    val name: String
): Parcelable