package com.yoenas.tokoonline.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.model.cart.DataCart
import com.yoenas.tokoonline.utils.GlideHelper
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartAdapter (val context: Context, var cart: ArrayList<DataCart>,
                   val clickListener: (DataCart, Int) -> Unit):
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.adapter_cart,
            parent, false)
    )

    override fun getItemCount() = cart.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(cart[position])
        GlideHelper.setImage(context, cart[position].gambar_produk!!, holder.imgCart)
        holder.imgDeleteCart.setOnClickListener {
            clickListener(cart[position], position)
            removeCart(position)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val tvCategoryCart: TextView = itemView.findViewById(R.id.tv_category_cart)
        private val tvNameProductCart: TextView = itemView.findViewById(R.id.tv_name_product_cart)
        private val tvPriceCart: TextView = itemView.findViewById(R.id.tv_price_cart)
        private val tvTotalCart: TextView = itemView.findViewById(R.id.tv_total_cart)
        val imgCart: ImageView = itemView.findViewById(R.id.img_cart)
        val imgDeleteCart: ImageView = itemView.findViewById(R.id.img_delete)

        fun bindItem(cart: DataCart){
            tvCategoryCart.text = cart.kategori
            tvNameProductCart.text = cart.nama_produk
            tvPriceCart.text = "${cart.harga_rupiah} x${cart.jumlah}"

            val total: Double = cart.jumlah!!.toDouble() * cart.harga!!.toDouble()
            val totalRupiah: String = NumberFormat.getNumberInstance(Locale.ENGLISH).format(total)
            tvTotalCart.text = "Rp$totalRupiah"
        }
    }

    fun setData(newCart: List<DataCart>){
        cart.clear()
        cart.addAll(newCart)
        notifyDataSetChanged()
    }

    fun removeCart(position: Int) {
        cart.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, cart.size)
    }

    fun removeAll(){
        cart.clear()
        notifyDataSetChanged()
    }
}