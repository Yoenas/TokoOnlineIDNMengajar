package com.yoenas.tokoonline.ui.transaction.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.infinityandroid.roundedimageview.RoundedImageView
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.model.transaction.detail.DataDetail
import com.yoenas.tokoonline.utils.GlideHelper
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class TransactionDetailAdapter (val context: Context, var detail: ArrayList<DataDetail>):
    RecyclerView.Adapter<TransactionDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.adapter_transaction_detail,
            parent, false)
    )

    override fun getItemCount() = detail.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(detail[position])
        GlideHelper.setImage(context, detail[position].gambar_produk!!, holder.imgTransactionDetail)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val tvCategory: TextView = itemView.findViewById(R.id.tv_cateogry_detail_transaction)
        private val tvNameProduct: TextView = itemView.findViewById(R.id.tv_name_product_transaction_detail)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price_transaction_detail)
        private val tvTotalDetailTransaction: TextView = itemView.findViewById(R.id.tv_total_transaction_detail)
        val imgTransactionDetail: ImageView = itemView.findViewById(R.id.img_transaction_detail)

        fun bindItem(detail: DataDetail){
            tvCategory.text = detail.kategori
            tvNameProduct.text = detail.nama_produk
            tvPrice.text = "${detail.harga_rupiah} x${detail.jumlah}"

            val total: Double = detail.jumlah!!.toDouble() * detail.harga!!.toDouble()
            val totalRupiah: String = NumberFormat.getNumberInstance(Locale.GERMANY).format(total)
            tvTotalDetailTransaction.text = "Rp $totalRupiah"
        }
    }

    fun setData(newDetail: List<DataDetail>){
        detail.clear()
        detail.addAll(newDetail)
        notifyDataSetChanged()
    }
}