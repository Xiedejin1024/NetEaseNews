package com.example.neteasenews.ui.home.native

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.neteasenews.logic.Repository
import com.example.neteasenews.logic.model.Native
import com.example.neteasenews.logic.model.NativeDetail
import com.example.neteasenews.logic.model.Sports
import com.example.neteasenews.logic.model.SportsDetail
import com.example.neteasenews.logic.network.HttpResponse
import com.example.neteasenews.logic.network.HttpUtil
import com.example.neteasenews.logic.network.UrlContance
import com.example.neteasenews.utils.LogUtil

class NativeViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<Native>()


    var nativeAddress = ""


    val nativeDetailList = ArrayList<NativeDetail>()

    val nativeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchNative(query)
    }


    private fun refreshNativeUI(native: Native) {
        searchLiveData.postValue(native)
    }


    fun getNativeData(startValue: Int, endValue: Int) {
        val httpUtil = HttpUtil.getInstance()
        UrlContance.getRefreshURL(UrlContance.Native_URL, startValue, endValue)
            ?.let {
                httpUtil?.doGetRequest(
                    it, object : HttpResponse<Native>(Native::class.java) {
                        override fun onError() {
                            LogUtil.e("it520com", "NativeViewModel数据请求失败")
                        }

                        override fun onSuccess(native: Native) {
                            refreshNativeUI(native)
                        }
                    })
            }
    }
}