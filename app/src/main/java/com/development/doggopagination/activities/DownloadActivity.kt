package com.development.doggopagination.activities

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.development.doggopagination.databinding.ActivityDownloadBinding
import com.development.doggopagination.viewmodel.DownloadViewModel
import com.development.doggopagination.viewmodel.MainViewModel
import com.development.doggopagination.viewmodel.throttle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class DownloadActivity : AppCompatActivity() {

    private val viewModel  by viewModels<DownloadViewModel>()

    private var job: Job? = null


    companion object {
        private const val BUFFER_LENGTH_BYTES = 1024 * 8
        private const val HTTP_TIMEOUT = 30
    }

    lateinit var binding: ActivityDownloadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myFile = cacheFile(context = this)
//        viewModel.downloadFileV2("https://www.hq.nasa.gov/alsj/a17/A17_FlightPlan.pdf",myFile)

        job = lifecycleScope.launch {
            viewModel.getDownloadPercentageFlow("https://www.hq.nasa.gov/alsj/a17/A17_FlightPlan.pdf",myFile)
                .throttle(1000)
                .onCompletion {
                    Log.d("flow","file is downloaded by app.")
                }
                .collectLatest {
                    binding.tvPercentage.text = "$it %"
                    binding.pbPercentage.setProgress(it, true)
                }
        }

        binding.button.setOnClickListener {
            job?.cancel()
        }

    }


    private fun cacheFile(context: Context) : File{
        val fileName: String = "download_"+System.currentTimeMillis().toString()
        val filesDir: File = context.cacheDir
        val cacheFile = File(filesDir, "$fileName.pdf")

        return cacheFile
    }
}