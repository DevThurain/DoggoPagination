package com.development.doggopagination.pagingsource

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.development.doggopagination.data.model.TheDoggoModelImpl
import com.development.doggopagination.data.vo.DoggoVO
import okio.IOException
import retrofit2.HttpException

class DoggoPagingSource() : PagingSource<Int, DoggoVO>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DoggoVO> {
        val page = params.key ?: 0
        val doggoModel = TheDoggoModelImpl()
        return try {
            val response = doggoModel.getDoggoList(page = page, size = 5)
            val doggoList = response.body() ?: emptyList()
            LoadResult.Page(
                doggoList, prevKey = if (page == 0) null else page - 1,
                nextKey = if(page > 2) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DoggoVO>): Int? {
        return null
    }
}

private fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(pageSize = 10, enablePlaceholders = true)
}

fun letDoggoImagesLiveData(pagingConfig: PagingConfig = getDefaultPageConfig()): LiveData<PagingData<DoggoVO>> {
    return Pager(
        config = pagingConfig,
        pagingSourceFactory = { DoggoPagingSource() }
    ).liveData
}

