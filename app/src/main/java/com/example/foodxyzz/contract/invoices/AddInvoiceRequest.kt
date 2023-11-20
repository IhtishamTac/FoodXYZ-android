package com.example.foodxyzz.contract.invoices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddInvoiceRequest(
    val items: List<Item>,
    val priceTotal: Int,
    val qtyTotal: Int
) : Parcelable