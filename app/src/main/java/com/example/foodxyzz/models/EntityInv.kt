package com.example.foodxyzz.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntityInv(
    val data_inv: List<DataInv>,
    val price_total: Int,
    val qty_total: Int
) : Parcelable