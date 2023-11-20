package com.example.foodxyzz.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataInv(
    val id: Int,
    val image: String,
    val name: String,
    val price: Int,
    val qty: Int,
    val rating: Double
) : Parcelable