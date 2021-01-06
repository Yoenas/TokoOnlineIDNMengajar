package com.yoenas.tokoonline.ui.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.model.transaction.DataTransaction

class TransactionAdapter(
    val context: Context, var transaction: ArrayList<DataTransaction>,
    val clickListener: (DataTransaction) -> Unit
) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_transaction,
            parent, false
        )
    )

    override fun getItemCount() = transaction.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(transaction[position], clickListener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val tvInvoiceTransaction: TextView = itemView.findViewById(R.id.tv_invoice_transaction)
        private val tvDateTransaction: TextView = itemView.findViewById(R.id.tv_date_transaction)
        private val tvTotalTransaction: TextView = itemView.findViewById(R.id.tv_total_transaction)
        val tvSeeTransaction: TextView = itemView.findViewById(R.id.tv_see_transaction)

        fun bindItem(transaction: DataTransaction, clickListener: (DataTransaction) -> Unit) {
            tvInvoiceTransaction.text = transaction.no_faktur
            tvDateTransaction.text = transaction.tgl_penjualan
            tvTotalTransaction.text = transaction.total_rupiah
            itemView.setOnClickListener {
                clickListener(transaction)
            }
//            val totalRupiah: String = NumberFormat.getNumberInstance(Locale.GERMANY)
//                .format(transaction.total!!.toDouble())
//            view.txvTotal.text = totalRupiah
        }
    }

    fun setData(newTransaction: List<DataTransaction>){
        transaction.clear()
        transaction.addAll(newTransaction)
        notifyDataSetChanged()
    }

    fun removeTransaction(position: Int) {
        transaction.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, transaction.size)
    }
}