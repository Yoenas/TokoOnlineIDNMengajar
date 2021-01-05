package com.yoenas.tokoonline.ui.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.model.product.DataCategory
import com.yoenas.tokoonline.utils.GlideHelper
import java.text.NumberFormat
import java.util.*

class CategoryAdapter(
    val context: Context,
    var category: ArrayList<DataCategory>,
    val clickListener: (DataCategory, Int) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_category,
            parent, false
        )
    )

    override fun getItemCount() = category.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(category[position])
        GlideHelper.setImage(context, category[position].gambar_kategori!!, holder.imgCategory)
        holder.rlCategory.setOnClickListener{
            Constant.CATEGORY_ID = category[position].kd_kategori!!
            clickListener(category[position], position)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategoryProduct: TextView = itemView.findViewById(R.id.tv_category)
        val imgCategory: ImageView = itemView.findViewById(R.id.img_category)
        val rlCategory: RelativeLayout = itemView.findViewById(R.id.rl_category)

        fun bindItem(category: DataCategory) {
            tvCategoryProduct.text = category.kategori
        }
    }

    fun setData(newCategory: List<DataCategory>) {
        category.clear()
        category.addAll(newCategory)
        notifyDataSetChanged()
    }
}