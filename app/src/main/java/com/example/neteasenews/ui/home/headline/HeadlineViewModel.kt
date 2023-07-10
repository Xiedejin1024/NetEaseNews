package com.example.neteasenews.ui.home.headline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.neteasenews.logic.Repository
import com.example.neteasenews.logic.model.Ads
import com.example.neteasenews.logic.model.Headline
import com.example.neteasenews.logic.model.HotsDetail
import com.example.neteasenews.logic.network.HttpResponse
import com.example.neteasenews.logic.network.HttpUtil
import com.example.neteasenews.logic.network.UrlContance
import com.example.neteasenews.utils.LogUtil

class HeadlineViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<Headline>()

    val hotsDetailList = ArrayList<HotsDetail>()

    val headlineLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchHeadline(query)
    }

    val bannerList = ArrayList<Ads>()

    private fun refreshHeadlineUI(headline: Headline) {
        searchLiveData.postValue(headline)
    }


    fun getHeadlineData(startValue: Int, endValue: Int) {
        val httpUtil = HttpUtil.getInstance()
        UrlContance.getRefreshURL(UrlContance.HOME_URL, startValue, endValue)?.let {
            httpUtil?.doGetRequest(
                it,
                object : HttpResponse<Headline>(Headline::class.java) {
                    override fun onError() {
                        LogUtil.e("it520com", "HeadlineViewModel数据请求失败")
                    }

                    override fun onSuccess(headline: Headline) {
                        refreshHeadlineUI(headline)
                    }
                })
        }
    }


}