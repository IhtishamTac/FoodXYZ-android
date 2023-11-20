package com.example.foodxyzz.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntityMenu(
    val id: Int,
    val image: String,
    val name: String,
    val price: Int,
    val rating: Double,
    var qyt : Int = 0
) : Parcelable