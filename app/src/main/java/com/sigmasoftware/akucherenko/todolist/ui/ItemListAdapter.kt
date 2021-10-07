package com.sigmasoftware.akucherenko.todolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sigmasoftware.akucherenko.todolist.databinding.ItemTodoBinding
import com.sigmasoftware.akucherenko.todolist.model.Item

class ItemListAdapter(private val itemList: MutableList<Item>) :
    RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ItemListAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) = with(binding) {
            doneCheck.isChecked = item.done
            itemContext.text = item.contentTask
            dateCreated.text = item.dateCreated.toString()
        }
    }

    fun deleteItem(position: Int) {
        itemList.removeAt(position)
    }

    fun updateItem(position: Int) {
        val item = itemList[position]
        item.done = true
        itemList[position] = item
    }
}