package com.example.neteasenews.ui.detail

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.neteasenews.logic.Repository
import com.example.neteasenews.logic.model.HotsDetail
import com.example.neteasenews.logic.network.HttpResponse
import com.example.neteasenews.logic.network.HttpUtil
import com.example.neteasenews.logic.network.UrlContance
import com.example.neteasenews.ui.freed.FreedBacks
import com.example.neteasenews.utils.LogUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


class DetailViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
    private val searchFreedLiveData = MutableLiveData<String>()

    val detailLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchDetailByDocId(query)
    }

    val freedBacksLiveData = Transformations.switchMap(searchFreedLiveData) { query ->
        Repository.searchFreedBackByDocId(query)
    }


    private fun refreshDetailUI(string: String) {
        searchLiveData.postValue(string)
    }

    private fun refreshFreedBackUI(responseStr: String) {
        searchFreedLiveData.postValue(responseStr)
    }


    fun getDetailData(docId: String) {
        if (!TextUtils.isEmpty(docId)) {
            HttpUtil.getInstance()?.doGetRequest(
                UrlContance.getDetailURL(docId),
                object : HttpResponse<String>(String::class.java) {
                    override fun onError() {
                        LogUtil.e("it520com", "DetailViewModel数据请求失败")
                    }

                    override fun onSuccess(responseStr: String) {
                        refreshDetailUI(responseStr)
                    }
                })
        }
    }

    fun getFreedBackData(docId: String) {
        if (!TextUtils.isEmpty(docId)) {
            HttpUtil.getInstance()?.doGetRequest(
                UrlContance.getFreedbackURL(docId),
                object : HttpResponse<String>(String::class.java) {
                    override fun onError() {
                        LogUtil.e("it520com", "DetailViewModel数据请求失败")
                    }

                    override fun onSuccess(responseStr: String) {
                        refreshFreedBackUI(responseStr)
                    }
                })
        }
    }

}