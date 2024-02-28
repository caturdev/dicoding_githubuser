package com.dicodingsubmit.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicodingsubmit.githubuser.data.parcel.User
import com.dicodingsubmit.githubuser.data.remote.response.UserItemResponse
import com.dicodingsubmit.githubuser.databinding.ListUserItemBinding
import com.dicodingsubmit.githubuser.ui.activity.UserDetailActivity

class UserAdapter : ListAdapter<UserItemResponse, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

	class ViewHolder(private val binding: ListUserItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(item: UserItemResponse) {
			// display username
			binding.tvUsername.text = item.login

			// display user ID
			"ID ${item.id}".also { binding.tvUserId.text = it }

			// display user avatar
			Glide
				.with(binding.profilePicture.context)
				.load(item.avatarUrl)
				.into(binding.profilePicture)

		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ListUserItemBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item)

		val userData = User(
			item.login
		)

		holder.itemView.setOnClickListener { view ->
			val moveIntent = Intent(holder.itemView.context, UserDetailActivity::class.java)
			moveIntent.putExtra(UserDetailActivity.GITHUB_USER, userData)
			view.context.startActivity(moveIntent)
		}
	}

	companion object {
		val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItemResponse>() {
			override fun areItemsTheSame(
				oldItem: UserItemResponse,
				newItem: UserItemResponse
			): Boolean {
				return oldItem == newItem
			}

			override fun areContentsTheSame(
				oldItem: UserItemResponse,
				newItem: UserItemResponse
			): Boolean {
				return oldItem == newItem
			}
		}
	}

}