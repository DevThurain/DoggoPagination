package com.development.doggopagination.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.development.doggopagination.R
import com.development.doggopagination.data.vo.DoggoVO
import com.development.doggopagination.databinding.ViewholderDoggoBinding

class DoggoViewHolder(itemView: View,val binding: ViewholderDoggoBinding): RecyclerView.ViewHolder(itemView) {

    fun bind(doggoVO: DoggoVO){
        Glide.with(itemView)
            .load(doggoVO.url)
            .placeholder(R.drawable.ic_cat)
            .into(binding.ivDoggo)
    }
}