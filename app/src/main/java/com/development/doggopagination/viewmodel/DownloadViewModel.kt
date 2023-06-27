package com.development.doggopagination.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.doggopagination.activities.DownloadActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class DownloadViewModel : ViewModel() {



    fun downloadFile(url: String, file: File){

        viewModelScope.launch(Dispatchers.IO){
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()
            val body = response.body
            val responseCode = response.code
            if (responseCode >= HttpURLConnection.HTTP_OK &&
                responseCode < HttpURLConnection.HTTP_MULT_CHOICE &&
                body != null) {
                val length = body.contentLength()
                body.byteStream().apply {
                    file.outputStream().use { fileOut ->
                        var bytesCopied = 0
                        val buffer = ByteArray(1024 * 8)
                        var bytes = read(buffer)
                        while (bytes >= 0) {
                            fileOut.write(buffer, 0, bytes)
                            bytesCopied += bytes
                            bytes = read(buffer)
                            Log.d("download", ((bytesCopied * 100)/length).toInt().toString())
//                        emitter.onNext(
//                            ((bytesCopied * 100)/length).toInt())
                        }
                    }
//                emitter.onComplete()
                }
            } else {
                // Report the error
            }
        }

    }

    fun getDownloadPercentageFlow(url: String, file: File) : Flow<Int>{
        return flow<Int> {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()
            val body = response.body
            val responseCode = response.code
            if (responseCode >= HttpURLConnection.HTTP_OK &&
                responseCode < HttpURLConnection.HTTP_MULT_CHOICE &&
                body != null) {
                val length = body.contentLength()
                body.byteStream().apply {
                    file.outputStream().use { fileOut ->
                        var bytesCopied = 0
                        val buffer = ByteArray(1024 * 8)
                        var bytes = read(buffer)
                        while (bytes >= 0) {
                            fileOut.write(buffer, 0, bytes)
                            bytesCopied += bytes
                            bytes = read(buffer)
                            val percentage = ((bytesCopied * 100)/length).toInt()
                            emit(percentage)
                        }
                    }
//                emitter.onComplete()
                }
            } else {
                // Report the error
            }
        }.flowOn(Dispatchers.IO)



    }

    fun downloadFileV2(url: String, file: File){
        viewModelScope.launch {
            getDownloadPercentageFlow(url, file)
                .throttle(1000)
                .onCompletion {
                    Log.d("flow","${file.name} is downloaded by app.")
                }
                .collectLatest {
                    Log.d("flow","$it percent")
            }
        }
    }


}

fun <T> Flow<T>.throttle(periodMillis: Long): Flow<T> {
    if (periodMillis < 0) return this
    return flow {
        conflate().collect { value ->
            emit(value)
            delay(periodMillis)
        }
    }
}