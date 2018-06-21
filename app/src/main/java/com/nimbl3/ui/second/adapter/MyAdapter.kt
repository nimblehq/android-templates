package com.nimbl3.ui.second.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nimbl3.R
import com.nimbl3.extension.loadImage
import com.nimbl3.ui.second.adapter.MyAdapter.MyViewHolder
import kotlinx.android.synthetic.main.adapter_item_recycler_view.view.*

class MyAdapter(private val myDataSet: Array<String>,
                listener: OnItemClickListener?) : RecyclerView.Adapter<MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

    val itemClickListener: OnItemClickListener? = listener

    override fun getItemCount() = myDataSet.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_item_recycler_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.bind(myDataSet[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { itemClickListener?.onItemClick(myDataSet[adapterPosition]) }
        }

        fun bind(data: String) {
            itemView.itemContent.text = data
            itemView.itemImage.loadImage("https://i.redd.it/qupjfpl4gvoy.jpg")
        }
    }
}
