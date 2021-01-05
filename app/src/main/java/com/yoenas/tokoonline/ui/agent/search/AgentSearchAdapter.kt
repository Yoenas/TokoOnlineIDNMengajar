package com.yoenas.tokoonline.ui.agent.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.model.agent.DataAgent
import com.yoenas.tokoonline.utils.GlideHelper

class AgentSearchAdapter(
    val context: Context,
    var dataAgent: ArrayList<DataAgent>,
    val clickListener: (DataAgent, Int) -> Unit
) : RecyclerView.Adapter<AgentSearchAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNameStore: TextView = view.findViewById(R.id.tv_name_store_search)
        private val tvLocation: TextView = view.findViewById(R.id.tv_location_search)
        val imgStore: ImageView = view.findViewById(R.id.img_agent_search)
        val cvAgentSearch: CardView = view.findViewById(R.id.cv_agent_search)

        fun bindItem(dataAgent: DataAgent) {
            tvNameStore.text = dataAgent.nama_toko
            tvLocation.text = dataAgent.alamat
            GlideHelper.setImage(itemView.context, dataAgent.gambar_toko!!, imgStore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_agent_search, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(dataAgent[position])
        holder.cvAgentSearch.setOnClickListener {
            Constant.AGENT_ID = dataAgent[position].kd_agen!!
            clickListener(dataAgent[position], position)
        }

    }

    override fun getItemCount() = dataAgent.size

    fun setData(newDataAgent: List<DataAgent>) {
        dataAgent.clear()
        dataAgent.addAll(newDataAgent)
        notifyDataSetChanged()
    }
}