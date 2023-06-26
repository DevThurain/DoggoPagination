package com.development.doggopagination.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.development.doggopagination.R
import com.development.doggopagination.data.vo.DoggoVO
import com.development.doggopagination.databinding.ViewholderDoggoBinding
import com.development.doggopagination.view.viewholder.DoggoViewHolder

class DoggoAdapter : RecyclerView.Adapter<DoggoViewHolder>() {
    var mTheDoggoList: List<DoggoVO> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoggoViewHolder {
        val binding = ViewholderDoggoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoggoViewHolder(itemView = binding.root, binding = binding)
    }

    override fun getItemCount(): Int {
        return  mTheDoggoList.size
    }

    override fun onBindViewHolder(holder: DoggoViewHolder, position: Int) {
        holder.bind(mTheDoggoList[position])
    }

    fun setNewData(doggoList: List<DoggoVO>){
        mTheDoggoList = doggoList
        notifyDataSetChanged()
    }
}