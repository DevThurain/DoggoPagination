package com.development.doggopagination.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*
import com.development.doggopagination.adapters.DoggoPagingAdapter
import com.development.doggopagination.data.model.TheDoggoModelImpl
import com.development.doggopagination.data.vo.DoggoVO
import com.development.doggopagination.pagingsource.DoggoPagingSource
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val doggoLiveData = MutableLiveData<List<DoggoVO>>()
    val mTheDoggoModel = TheDoggoModelImpl()

//    fun getDoggoList(){
//        viewModelScope.launch {
//            val respond = mTheDoggoModel.getDoggoList(page = 0, size = 12)
//            if(respond.isSuccessful){
//                doggoLiveData.value = respond.body()
//            }
//        }
//    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 10, enablePlaceholders = false)
    }

    fun letDoggoImagesLiveData(pagingConfig: PagingConfig = getDefaultPageConfig()): LiveData<PagingData<DoggoVO>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { DoggoPagingSource() }
        ).liveData
    }



}