package com.example.neteasenews.ui.home.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.neteasenews.logic.Repository
import com.example.neteasenews.logic.model.Sports
import com.example.neteasenews.logic.model.SportsDetail
import com.example.neteasenews.logic.network.HttpResponse
import com.example.neteasenews.logic.network.HttpUtil
import com.example.neteasenews.logic.network.UrlContance
import com.example.neteasenews.utils.LogUtil

class SportsViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<Sports>()

    val sportDetailList = ArrayList<SportsDetail>()

    val sportsLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchSports(query)
    }


    private fun refreshSportsUI(sports: Sports) {
        searchLiveData.postValue(sports)
    }


    fun getHeadlineData(startValue: Int, endValue: Int) {
        val httpUtil = HttpUtil.getInstance()
        UrlContance.getRefreshURL(UrlContance.SPORTS_URL, startValue, endValue)?.let {
            httpUtil?.doGetRequest(
                it,
                object : HttpResponse<Sports>(Sports::class.java) {
                    override fun onError() {
                        LogUtil.e("it520com", "SportsViewModel数据请求失败")
                    }

                    override fun onSuccess(sports: Sports) {
                        refreshSportsUI(sports)
                    }
                })
        }
    }

}