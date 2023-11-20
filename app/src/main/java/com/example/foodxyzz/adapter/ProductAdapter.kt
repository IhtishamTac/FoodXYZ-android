package com.example.foodxyzz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodxyzz.R
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ProductAdapter {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val product = productList[position]
//        holder.bind(product)
//    }
//
//    override fun getItemCount(): Int {
//        return productList.size
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val imageView: ImageView = itemView.findViewById(R.id.productPict)
//        private val nameTextView: TextView = itemView.findViewById(R.id.productName)
//        private val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
//        private val ratingTextView: TextView = itemView.findViewById(R.id.productRating)
//
//        fun bind(product: Product) {
//            // Load image using Picasso or any other image loading library
//            Picasso.get().load(product.image).into(imageView)
//
//            nameTextView.text = product.name
//            priceTextView.text = product.price.toString()
//            ratingTextView.text = product.rating.toString()
//        }
//    }
}