package com.marcosfigueroa.mueblesdres.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.marcosfigueroa.mueblesdres.LoginActivity
import com.marcosfigueroa.mueblesdres.R
import com.marcosfigueroa.mueblesdres.model.Mueble
import kotlinx.android.synthetic.main.row_layout.view.*

class MyAdapter(var clickListener: OnItemClickListener): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var mueblesList = emptyList<Mueble>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nombre = itemView.nombre_txt
        var color = itemView.color_txt
        var precio = itemView.precio_txt

        fun initialize(item: Mueble, action:OnItemClickListener) {
            nombre.text = item.nombre
            color.text = item.color
            precio.text = item.precio
            itemView.setOnClickListener {
                action.itemClick(item, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        var item = mueblesList[position]
        holder.initialize(mueblesList.get(position), clickListener)
    }

    override fun getItemCount(): Int {
        return mueblesList.size
    }

    fun setData(newList: List<Mueble>) {
        mueblesList = newList
        notifyDataSetChanged()
    }
}

interface OnItemClickListener {
    fun itemClick(item: Mueble, position: Int) {}
}