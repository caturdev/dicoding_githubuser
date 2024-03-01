package com.dicodingsubmit.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsubmit.githubuser.databinding.ListHistoryItemBinding
import com.dicodingsubmit.githubuser.ui.adapter.utils.HistoryClickListener

class HistoryAdapter :
	ListAdapter<String, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

	var listener: HistoryClickListener? = null

	class ViewHolder(
		private val binding: ListHistoryItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: String) {
			// display username keyword
			binding.tvUsername.text = item
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ListHistoryItemBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item)

		holder.itemView.setOnClickListener {
			listener?.onItemClicked(it, item)
		}
	}

	companion object {
		val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
			override fun areItemsTheSame(
				oldItem: String,
				newItem: String
			): Boolean {
				return oldItem == newItem
			}

			override fun areContentsTheSame(
				oldItem: String,
				newItem: String
			): Boolean {
				return oldItem == newItem
			}
		}
	}

}