package com.yoenas.tokoonline.ui.agent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.model.agent.DataAgent
import com.yoenas.tokoonline.utils.GlideHelper

class AgentAdapter(
    val context: Context,
    var dataAgent: ArrayList<DataAgent>,
    val clickListener: (DataAgent, Int, String) -> Unit
) : RecyclerView.Adapter<AgentAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNameStore: TextView = view.findViewById(R.id.tv_name_store)
        private val tvLocation: TextView = view.findViewById(R.id.tv_location)
        val imgStore: ImageView = view.findViewById(R.id.img_agent)
        val tvOptions: TextView = view.findViewById(R.id.tv_options)

        fun bindItem(dataAgent: DataAgent) {
            tvNameStore.text = dataAgent.nama_toko
            tvLocation.text = dataAgent.alamat
            GlideHelper.setImage(itemView.context, dataAgent.gambar_toko!!, imgStore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_agent, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(dataAgent[position])
        holder.imgStore.setOnClickListener {
            Constant.AGENT_ID = dataAgent[position].kd_agen!!
            clickListener(dataAgent[position], position, "detail")
        }
        holder.tvOptions.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.tvOptions)
            popupMenu.inflate(R.menu.menu_options)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_update -> {
                        Constant.AGENT_ID = dataAgent[position].kd_agen!!
                        clickListener(dataAgent[position], position, "update")
                    }

                    R.id.action_delete -> {
                        Constant.AGENT_ID = dataAgent[position].kd_agen!!
                        clickListener(dataAgent[position], position, "delete")
                    }
                }
                true
            }
            popupMenu.show()
        }

    }

    override fun getItemCount() = dataAgent.size

    fun setData(newDataAgent: List<DataAgent>) {
        dataAgent.clear()
        dataAgent.addAll(newDataAgent)
        notifyDataSetChanged()
    }

    fun deleteAgent(position: Int){
        dataAgent.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataAgent.size)
    }
}