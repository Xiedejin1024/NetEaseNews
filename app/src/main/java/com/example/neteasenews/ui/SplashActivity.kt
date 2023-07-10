package com.example.neteasenews.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.Data
import com.example.neteasenews.R
import com.example.neteasenews.databinding.ActivitySplashBinding
import com.example.neteasenews.logic.model.Headline
import com.example.neteasenews.logic.network.UrlContance
import com.example.neteasenews.logic.model.splash.Ads
import com.example.neteasenews.logic.model.splash.AdsDetail
import com.example.neteasenews.logic.model.splash.DownloadImageService
import com.example.neteasenews.logic.network.HttpResponse
import com.example.neteasenews.logic.network.HttpUtil
import com.example.neteasenews.ui.splash.SplashViewModel
import com.example.neteasenews.utils.FileUtil
import com.example.neteasenews.utils.LogUtil
import com.example.neteasenews.utils.MD5util
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.IOException


class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null

    private val binding: ActivitySplashBinding
        get() = _binding!!

    val viewModel by lazy { ViewModelProvider(this).get(SplashViewModel::class.java) }

    private val splashDuration = 3 * 1000L


    //缩放
    private val scaleAnimation by lazy {
        ScaleAnimation(
            1f,
            1.15f,
            1f,
            1.15f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = splashDuration
            repeatCount = -1
            interpolator = DecelerateInterpolator()
            fillAfter = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .statusBarColor(R.color.black)
            .fitsSystemWindows(true)
            .init()
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rlSplashJump.startAnimation(scaleAnimation)

        initData()

        getAds()

    }


    private fun initData() {
        var index: Int = viewModel.getIndex()
        val ads = viewModel.getSaveAds()
        if (TextUtils.isEmpty(ads)) {//没有请求到数据,说明需要去网络请求
            binding.imageSplashUi.setBackgroundResource(R.mipmap.splash)
            lifecycleScope.launch {
                delay(splashDuration)
                MainActivity.start(this@SplashActivity)
                finish()
            }
        } else {
            val gson = Gson()
            val ads = gson.fromJson(ads, Ads::class.java)
            val adsDetails = ads.ads
            if (adsDetails.isNotEmpty()) {
                index %= adsDetails.size

                val detail = adsDetails[index]
                val materialList = detail.materialList[0]//获取图片的列表即可
                val imagerUrl = materialList.urls[0]

                //采用md5生成本地唯一的文件名称
                val imageName = MD5util.md52Str(imagerUrl)
                if (FileUtil.checkDownload(applicationContext, imageName)) {
                    val file: File? = FileUtil.getImageByName(applicationContext, imageName)
                    if (file != null) {
                        if (file.exists()) {
                            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                            if (bitmap != null) {
                                index++
                                viewModel.saveIndex(index)
                                binding.imageSplashUi.setImageBitmap(bitmap)
                                lifecycleScope.launch {
                                    delay(splashDuration)
                                    MainActivity.start(this@SplashActivity)
                                    finish()
                                }
                                //点击图片跳转
                                binding.rlSplashJump.setOnClickListener {
                                    skipUI(detail)
                                }
                            }
                        }
                    }

                } else {
                    index++
                    viewModel.saveIndex(index)
                    binding.imageSplashUi.setBackgroundResource(R.mipmap.splash)
                    lifecycleScope.launch {
                        delay(splashDuration)
                        MainActivity.start(this@SplashActivity)
                        finish()
                    }
                }
            } else {
                binding.imageSplashUi.setBackgroundResource(R.mipmap.splash)
                lifecycleScope.launch {
                    delay(splashDuration)
                    MainActivity.start(this@SplashActivity)
                    finish()
                }
            }

        }
    }

    private fun skipUI(adsDetail: AdsDetail) {
        val actionList = adsDetail.actionList
        val action = actionList[0]
        if (action == null || TextUtils.isEmpty(action.url)) {
            return
        }
        lifecycleScope.cancel()
        Intent(this@SplashActivity, WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.ACTION_URL, action.url)
            startActivity(this)
            finish()
        }
    }

    private fun getAds() {
        val ads = viewModel.getSaveAds()
        if (TextUtils.isEmpty(ads)) {//没有请求到数据,说明需要去网络请求
            getData()
        } else {
            //有数据存在,判断是否超时,如果超时就去重新获取
            val lastTime = viewModel.getLastTime()
            val timeOut = viewModel.getTimeOut()
            val currentTime = System.currentTimeMillis()
            val space = currentTime - lastTime
            if (space > (timeOut * 60 * 1000)) {
                //超时了,重新获取
                getData()
            }
        }
    }

    private fun getData() {
        val httpUtil = HttpUtil.getInstance()
        httpUtil?.doGetRequest(UrlContance.SPLASH_URL,
            object : HttpResponse<String>(String::class.java) {
                override fun onError() {
                    LogUtil.e("it520com", "SplashActivity数据请求失败")
                }

                override fun onSuccess(responseData: String) {
                    showResponse(responseData)
                }
            })

    }

    private fun showResponse(responseData: String) {
        val gson = Gson()
        val ads = gson.fromJson(responseData, Ads::class.java)
        //保存数据
        viewModel.saveAds(responseData)
        val lastTime = System.currentTimeMillis()
        viewModel.saveLastTime(lastTime)
        viewModel.saveTimeOut(ads.next_req)

        val intent = Intent(this@SplashActivity, DownloadImageService::class.java)
        intent.putExtra(DownloadImageService.SPLASH_DATAS, ads)
        startService(intent)


        //  限制了最大值,超过时抛出了异常(Data cannot occupy more than 10240 bytes when serialized)
        //  public static final int MAX_DATA_BYTES = 10 * 1024;    // 10KB
        /*val request = OneTimeWorkRequest.Builder(DownLoadImageWorker::class.java)
            .setInputData(createInputDataForUri(responseData))
            .build()
        WorkManager.getInstance(context).enqueue(request)*/
    }

    private fun createInputDataForUri(responseData: String): Data {
        val myData = Data.Builder()
            .putString(UrlContance.SPLASH_WORKREQUEST, responseData)

        return myData.build()
    }

    override fun onDestroy() {
        super.onDestroy()
        scaleAnimation.cancel()
        _binding = null
    }

}


