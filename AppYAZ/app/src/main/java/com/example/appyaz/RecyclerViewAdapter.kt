package com.example.appyaz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appyaz.databinding.ModelItemBinding

class RecyclerViewAdapter(
    private val onItemClick: (ItemModelUAZ) -> Unit,
    private val onDeleteClick: (ItemModelUAZ) -> Unit
) : ListAdapter<ItemModelUAZ, RecyclerViewAdapter.ModelViewHolder>(DiffCallback) {

    inner class ModelViewHolder(private val binding: ModelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ItemModelUAZ) {
            binding.textViewModelName.text = model.name
            binding.textViewModelCost.text = model.cost

            binding.deleteButton.setOnClickListener { onDeleteClick(model) }
            binding.root.setOnClickListener { onItemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val binding = ModelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ItemModelUAZ>() {
        override fun areItemsTheSame(oldItem: ItemModelUAZ, newItem: ItemModelUAZ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemModelUAZ, newItem: ItemModelUAZ): Boolean {
            return oldItem == newItem
        }
    }
}