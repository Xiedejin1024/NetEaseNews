package com.example.neteasenews.ui.freed

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.neteasenews.logic.Repository
import com.example.neteasenews.logic.model.HotsDetail
import com.example.neteasenews.logic.network.HttpResponse
import com.example.neteasenews.logic.network.HttpUtil
import com.example.neteasenews.logic.network.UrlContance
import com.example.neteasenews.utils.LogUtil


class FreedViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val hotsDetailList = ArrayList<HotsDetail>()

    val detailLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchDetailByDocId(query)
    }


    private fun refreshDetailUI(string: String) {
        searchLiveData.postValue(string)
    }


    fun getFreedData(docId: String) {
        if (!TextUtils.isEmpty(docId)) {
            HttpUtil.getInstance()?.doGetRequest(
                UrlContance.getDetailURL(docId),
                object : HttpResponse<String>(String::class.java) {
                    override fun onError() {
                        LogUtil.e("it520com", "FreedViewModel数据请求失败")
                    }

                    override fun onSuccess(responseStr: String) {
                        refreshDetailUI(responseStr)
                    }
                })
        }
    }


}