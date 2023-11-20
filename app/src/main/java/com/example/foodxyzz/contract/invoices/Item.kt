package com.example.foodxyzz.contract.invoices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val productId: Int,
    val qty: Int,
    val subTotal: Int
) : Parcelable