package com.example.neteasenews.logic.network

import com.example.neteasenews.logic.model.Headline
import com.example.neteasenews.utils.LogUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


object HttpUtil {


    private var mHttputil: HttpUtil? = null
    private var mClient: OkHttpClient? = null

    init {
        mClient = OkHttpClient()
    }

    fun getInstance(): HttpUtil? {
        if (mHttputil == null) {
            synchronized(HttpUtil::class.java) {
                if (mHttputil == null) {
                    mHttputil = HttpUtil
                }
            }
        }
        return mHttputil
    }

    fun <T> doGetRequest(url: String, httpResponse: HttpResponse<T>) {
        val request = Request.Builder()
            .url(url)
            .build()
        mClient!!.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                httpResponse.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    httpResponse.onError()
                }
                val jsonStr = response.body!!.string()
                httpResponse.parseData(jsonStr)
            }
        })
    }

}