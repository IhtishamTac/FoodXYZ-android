package com.example.foodxyzz.ul

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.foodxyzz.Connect
import com.example.foodxyzz.InvoiceActivity
import com.example.foodxyzz.R
import com.example.foodxyzz.RegisterActivity
import com.example.foodxyzz.contract.invoices.AddInvoiceRequest
import com.example.foodxyzz.contract.invoices.Item
import com.example.foodxyzz.databinding.FragmentHomeBinding
import com.example.foodxyzz.models.EntityMenu
import com.google.gson.Gson
import java.text.DecimalFormat
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var txtTotal : TextView
    private lateinit var searchView : SearchView
    private var entityMenu : ArrayList<EntityMenu> = arrayListOf()
    private var entityMenuFiltered : ArrayList<EntityMenu> = arrayListOf()
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btnBayar = view.findViewById<LinearLayout>(R.id.btnLinBayar)
        searchView = view.findViewById(R.id.cariMenu)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return true
            }
            fun filter(search : String){
                entityMenuFiltered.clear()
                entityMenuFiltered.addAll(entityMenu.filter { it.name.lowercase().startsWith(search.lowercase()) })
                recyclerView.adapter?.notifyDataSetChanged()
            }
        })
        txtTotal = view.findViewById(R.id.txtHargaBayar)
        recyclerView = view.findViewById(R.id.homeRecycler)
        recyclerView.adapter = ListMenuAdapter(this@HomeFragment, entityMenu, entityMenuFiltered, txtTotal)
        getFood()

        btnBayar.setOnClickListener{
            if(entityMenu.filter { it.qyt > 0 }.size == 0){
                Toast.makeText(activity, "Mohon pilih menu anda!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var items : ArrayList<Item> = arrayListOf()
            for (a in entityMenu.filter { it.qyt > 0 }){
                items.add(Item(
                    a.id,
                    a.qyt,
                    a.price * a.qyt
                ))
            }
            var request = AddInvoiceRequest(items.toList(), items.sumOf { it.subTotal }, items.sumOf { it.qty })
            thread {
                val gson = Gson()
                Log.d("add-invoice", gson.toJson(request).toString())
                var status = Connect.saveInvoice(request)
                if (status != null){
                    activity?.runOnUiThread{
                        Toast.makeText(activity, "Berhasil menyimpan transaksi", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(activity, InvoiceActivity::class.java).apply {
                            putExtra("data", status)
                        })
                    }
                }else{
                    activity?.runOnUiThread{
                        Toast.makeText(activity, "Gagal menyimpan transaksi", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view
    }

    private fun getFood(){
        thread {
            try {
                val data = Connect.getMenu()

                for(i in 0 .. data.size - 1){
                    var x = data.get(i)
                    var menu = entityMenu.find{it.id == x.id}
                    if(menu != null){
                        x.qyt = menu.qyt
                    }
                }

                entityMenu.clear()
                entityMenu.addAll(data)
                entityMenuFiltered.clear()
                entityMenuFiltered.addAll(entityMenu)
                activity?.runOnUiThread{
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }catch (ex : Exception){
                Log.d("err-getFood", ex.toString())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

class ListMenuAdapter(
    val homeFragment: HomeFragment,
    val entityMenu: java.util.ArrayList<EntityMenu>,
    val entityMenuFiltered: java.util.ArrayList<EntityMenu>,
    val txtTotal: TextView
) : RecyclerView.Adapter<ListMenuHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMenuHolder {
        return ListMenuHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return entityMenuFiltered.size
    }

    override fun onBindViewHolder(holder: ListMenuHolder, position: Int) {
        val data = entityMenuFiltered.get(position)
        val data_real = entityMenu.find{it.id == data.id}

        with(holder) {
            txtNamaMenu.setText(data.name)
            txtQty.setText(data_real?.qyt.toString())
            txtHarga.setText("Rp. " + DecimalFormat("#.###").format(data.price))

            btnAddQty.setOnClickListener{
                var qty = txtQty.text.toString().toInt()
                qty++
                txtQty.setText(qty.toString())
            }
            btnRedQty.setOnClickListener{
                var qty = txtQty.text.toString().toInt()
                if(qty > 0){
                    qty--
                    txtQty.setText(qty.toString())
                }
            }
            btnAddKeranjang.setOnClickListener{
                if(data_real != null){
                    data_real.qyt = txtQty.text.toString().toInt()
                    txtTotal.setText("Rp. " + DecimalFormat("#.###").format(entityMenu.sumOf { it.price * it.qyt }))
                }
            }

            Glide.with(homeFragment)
                .load("${Connect.BASE_URL}/images/${data.image}")
                .into(imgMenu)
        }
    }

}

class ListMenuHolder(itemView: View) : ViewHolder(itemView) {
    val txtNamaMenu = itemView.findViewById<TextView>(R.id.productName)
    val txtHarga = itemView.findViewById<TextView>(R.id.productPrice)
    val txtQty = itemView.findViewById<TextView>(R.id.productQty)
    val btnAddKeranjang = itemView.findViewById<ImageView>(R.id.btnKeranjang)
    val imgMenu = itemView.findViewById<ImageView>(R.id.productPict)
    val btnAddQty = itemView.findViewById<ImageView>(R.id.addQty)
    val btnRedQty = itemView.findViewById<ImageView>(R.id.redQty)
}
