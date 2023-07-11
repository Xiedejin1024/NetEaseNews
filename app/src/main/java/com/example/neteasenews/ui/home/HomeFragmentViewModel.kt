package com.example.neteasenews.ui.home

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

class HomeFragmentViewModel : ViewModel() {


    fun saveHomeTitle(homeTitle: String) = Repository.saveHomeTitle(homeTitle)

    fun getHomeTitle(): String? = Repository.getHomeTitle()

    fun saveHideHomeTitle(hideTitle: String) = Repository.saveHideHomeTitle(hideTitle)

    fun getHideHomeTitle(): String? = Repository.getHideHomeTitle()


    val isRefreshFragment = MutableLiveData<Boolean>()


    fun refreshHeadlineUI(refresh: Boolean) {
        isRefreshFragment.value = refresh
    }


}