package com.example.neteasenews.ui.splash

import androidx.lifecycle.ViewModel
import com.example.neteasenews.logic.Repository
import com.example.neteasenews.logic.dao.SplashDao
import com.example.neteasenews.logic.model.splash.Ads
import com.google.gson.Gson

class SplashViewModel : ViewModel() {

    fun saveAds(adsString: String) = Repository.saveAds(adsString)

    fun getSaveAds(): String? = Repository.getSaveAds()

    fun saveLastTime(lastTime: Long) = Repository.saveLastTime(lastTime)
    fun getLastTime(): Long = Repository.getLastTime()

    fun saveTimeOut(timeOut: Int) = Repository.saveTimeOut(timeOut)
    fun getTimeOut(): Int = Repository.getTimeOut()


    fun saveIndex(index: Int) = Repository.saveIndex(index)
    fun getIndex(): Int = Repository.getIndex()

}