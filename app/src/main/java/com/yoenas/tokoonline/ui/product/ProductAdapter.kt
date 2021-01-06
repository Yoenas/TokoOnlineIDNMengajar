package com.yoenas.tokoonline.ui.product

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.model.product.DataProduct
import com.yoenas.tokoonline.utils.GlideHelper
import java.util.*

class ProductAdapter(
    val context: Context,
    var product: ArrayList<DataProduct>
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_product,
            parent, false
        )
    )

    override fun getItemCount() = product.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(product[position])
        GlideHelper.setImage(context, product[position].gambar_produk!!, holder.imgProduct)
        holder.linearProduct.setOnClickListener{
            Constant.PRODUCT_ID = product[position].kd_produk!!
            Constant.PRODUCT_ID = product[position].kd_produk!!
            Constant.PRODUCT_NAME = product[position].nama_produk!!
            Constant.IS_CHANGE = true
            (context as Activity).finish()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNameProduct: TextView = itemView.findViewById(R.id.tv_name_product)
        val tvPriceProduct: TextView = itemView.findViewById(R.id.tv_price_product)
        val tvStockProduct: TextView = itemView.findViewById(R.id.tv_stock_product)
        val imgProduct: ImageView = itemView.findViewById(R.id.img_product)
        val linearProduct: LinearLayout = itemView.findViewById(R.id.linear_product)

        fun bindItem(product: DataProduct) {
            tvNameProduct.text = product.nama_produk
            tvPriceProduct.text = product.harga_rupiah
            tvStockProduct.text = "Stok tersedia: ${product.stok}"
        }
    }

    fun setData(newProduct: List<DataProduct>) {
        product.clear()
        product.addAll(newProduct)
        notifyDataSetChanged()
    }
}