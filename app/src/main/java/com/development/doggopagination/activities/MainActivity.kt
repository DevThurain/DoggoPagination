package com.development.doggopagination.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import com.development.doggopagination.R
import com.development.doggopagination.adapters.DoggoAdapter
import com.development.doggopagination.adapters.DoggoPagingAdapter
import com.development.doggopagination.data.model.TheDoggoModelImpl
import com.development.doggopagination.databinding.ActivityMainBinding
import com.development.doggopagination.pagingsource.letDoggoImagesLiveData
import com.development.doggopagination.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel  by viewModels<MainViewModel>()
    private lateinit var mDoggoAdapter: DoggoAdapter
    private lateinit var mDoggoPagingAdapter: DoggoPagingAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpRecyclerView()
        setUpViewModel()
        setUpLiveData()


    }

    private fun setUpRecyclerView(){
//        mDoggoAdapter = DoggoAdapter()
//        binding.rvDoggoList.adapter = mDoggoAdapter

        mDoggoPagingAdapter = DoggoPagingAdapter()
        binding.rvDoggoList.adapter = mDoggoPagingAdapter
    }
    private fun setUpViewModel(){
//        viewModel.getDoggoList()
    }

    private fun setUpLiveData(){
        viewModel.letDoggoImagesLiveData().distinctUntilChanged().observe(this){
            lifecycleScope.launch {
                mDoggoPagingAdapter.submitData(it)
            }
        }

    }
}