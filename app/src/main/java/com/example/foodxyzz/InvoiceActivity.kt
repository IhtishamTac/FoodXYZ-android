package com.example.foodxyzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.foodxyzz.contract.invoices.AddInvoiceRequest
import com.example.foodxyzz.contract.invoices.Item
import com.example.foodxyzz.databinding.ActivityInvoiceBinding
import com.example.foodxyzz.models.EntityInv
import com.example.foodxyzz.models.EntityMenu
import kotlin.concurrent.thread

class InvoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInvoiceBinding
    private lateinit var recyclerView: RecyclerView
    var entityMenu: ArrayList<EntityMenu> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgInvBack.setOnClickListener{
            startActivity(Intent(this@InvoiceActivity, HomeActivity::class.java))
        }

        val dataInvoice = intent.getParcelableExtra<EntityInv>("data")
        if(dataInvoice != null){
            binding.invRecycler.adapter = ListInvoiceAdapter(this, dataInvoice)
        }

    }


}

class ListInvoiceAdapter(private val invoiceActivity: InvoiceActivity, val data : EntityInv) :
    RecyclerView.Adapter<ListInvoiceHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListInvoiceHolder {
        return ListInvoiceHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_1, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.data_inv.size
    }

    override fun onBindViewHolder(holder: ListInvoiceHolder, position: Int) {
        val dataInvoice = data.data_inv[position]
        with(holder) {
            txtInvName.text = "${dataInvoice?.name}"
            txtInvPrice.text = "${dataInvoice?.price}"
            txtInvQty.text = "${dataInvoice.qty}"
            txtInvSubTotal.text = "${dataInvoice.price * dataInvoice.qty}"
        }
    }

}


class ListInvoiceHolder(itemView: View) : ViewHolder(itemView) {
    val txtInvName = itemView.findViewById<TextView>(R.id.invProductName)
    val txtInvPrice = itemView.findViewById<TextView>(R.id.invProductPrice)
    val txtInvQty = itemView.findViewById<TextView>(R.id.invProductQty)
    val txtInvSubTotal = itemView.findViewById<TextView>(R.id.invProductSubTotal)
}


