package co.nimblehq.rxjava.ui.screens.home

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.extension.loadImage
import co.nimblehq.rxjava.ui.common.ItemClickable
import co.nimblehq.rxjava.ui.common.ItemClickableImpl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_data.*

internal class DataAdapter :
    RecyclerView.Adapter<DataAdapter.ViewHolder>(),
    ItemClickable<DataAdapter.OnItemClick> by ItemClickableImpl() {

    var items = listOf<Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    internal inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View
            get() = itemView

        init {
            itemView.setOnClickListener {
                notifyItemClick(OnItemClick.Item(items[adapterPosition]))
            }
        }

        fun bind(model: Data) {
            with(model) {
                tvDataTitle.text = title
                tvDataAuthor.text = author
                ivDataThumbnail.loadImage(thumbnail)
            }
        }
    }

    sealed class OnItemClick {

        data class Item(val data: Data) : OnItemClick()
    }
}
