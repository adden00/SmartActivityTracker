package com.example.smartactivitytracker.bluetooth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartactivitytracker.R
import com.example.smartactivitytracker.data.ListItem
import com.example.smartactivitytracker.databinding.ListtItemBinding

class RcAdapter(private val listener: Listener): ListAdapter<ListItem, RcAdapter.ItemHolder>(
    ItemComparator()
) {


    class ItemHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ListtItemBinding.bind(view)

        fun setData(item: ListItem, listener: Listener) = with(binding) {
            tvName.text = item.name
            tvMac.text = item.mac
            itemView.setOnClickListener{
                listener.onClick(item)
            }
        }
        companion object{
            fun create(parrent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parrent.context).inflate(
                    R.layout.listt_item,
                    parrent, false))
            }
        }
    }
    class ItemComparator: DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.mac == newItem.mac
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    interface Listener{
        fun onClick(item: ListItem)
    }

}