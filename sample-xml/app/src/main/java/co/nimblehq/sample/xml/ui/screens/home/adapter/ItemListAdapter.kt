package co.nimblehq.sample.xml.ui.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.nimblehq.sample.xml.databinding.ItemHomeListBinding
import co.nimblehq.sample.xml.model.UiModel

class ItemListAdapter constructor(var onItemClicked: ((uiModel: UiModel) -> Unit)? = null) :
    ListAdapter<UiModel, ItemListAdapter.ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }

    class ItemViewHolder(private val binding: ItemHomeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UiModel, onItemClicked: ((uiModel: UiModel) -> Unit)?) {
            binding.tvItemHome.text = item.id
            binding.root.setOnClickListener { onItemClicked?.invoke(item) }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<UiModel>() {
        override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel) =
            oldItem.id == newItem.id
    }
}
