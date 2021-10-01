package com.sigmasoftware.akucherenko.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sigmasoftware.akucherenko.todolist.databinding.ActivityMainBinding
import com.sigmasoftware.akucherenko.todolist.model.Item
import com.sigmasoftware.akucherenko.todolist.ui.ItemListAdapter
import com.sigmasoftware.akucherenko.todolist.ui.SwipeItem
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val itemsList: MutableList<Item> = mutableListOf()
    private lateinit var itemListAdapter: ItemListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemListAdapter = ItemListAdapter(itemsList)
        binding.recyclerView.adapter = itemListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.fab.setOnClickListener { inputNewLine() }

        ItemTouchHelper(swipeItem).attachToRecyclerView(binding.recyclerView)

    }

    private fun inputNewLine() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.item_message)

        val input = EditText(this)
        input.hint = getString(R.string.new_message_hint)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton(getString(android.R.string.ok)) { _, _ ->
            itemsList.add(
                Item(
                    contentTask = input.text.toString(),
                    done = false,
                    dateCreated = Date()
                )
            )
            itemListAdapter.notifyItemInserted(itemsList.size - 1)
        }

        builder.setNegativeButton(
            getString(android.R.string.cancel)
        ) { dialog, _ -> dialog.cancel() }
        builder.show()

    }

    val swipeItem = object : SwipeItem() {

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            when (direction) {
                ItemTouchHelper.START -> {
                    itemListAdapter.deleteItem(viewHolder.adapterPosition)
                }
                ItemTouchHelper.END -> {
                    itemListAdapter.updateItem(viewHolder.adapterPosition)
                }
            }
            super.onSwiped(viewHolder, direction)
            itemListAdapter.notifyDataSetChanged()
        }
    }
}