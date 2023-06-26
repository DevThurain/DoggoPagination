package com.development.doggopagination.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.doggopagination.R
import com.development.doggopagination.data.vo.DoggoVO
import com.development.doggopagination.databinding.ViewholderDoggoBinding
import com.development.doggopagination.view.viewholder.DoggoViewHolder

class DoggoPagingAdapter : PagingDataAdapter<DoggoVO, DoggoViewHolder>(REPO_COMPARATOR) {
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<DoggoVO>() {
            override fun areItemsTheSame(oldItem: DoggoVO, newItem: DoggoVO): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: DoggoVO, newItem: DoggoVO): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoggoViewHolder {
        val binding = ViewholderDoggoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoggoViewHolder(itemView = binding.root, binding = binding)
    }


    override fun onBindViewHolder(holder: DoggoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}