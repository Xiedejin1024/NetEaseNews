package com.example.neteasenews.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.neteasenews.NetEaseApplication
import com.example.neteasenews.logic.model.splash.Ads
import com.google.gson.Gson

object SplashDao {

    fun saveAds(adsString: String) {
        sharedPreferences().edit {
            putString("ads", adsString)
        }
    }

    fun getSaveAds(): String? {
        return sharedPreferences().getString("ads", "")

    }


    fun saveLastTime(lastTime: Long) {
        sharedPreferences().edit {
            putLong("last_time", lastTime)
        }
    }

    fun getLastTime(): Long {
        return sharedPreferences().getLong("last_time", 0)
    }


    fun saveTimeOut(timeOut: Int) {
        sharedPreferences().edit {
            putInt("time_out", timeOut)
        }
    }

    fun getTimeOut(): Int {
        return sharedPreferences().getInt("time_out", 0)
    }


    fun saveIndex(index: Int) {
        sharedPreferences().edit {
            putInt("index", index)
        }
    }

    fun getIndex(): Int {
        return sharedPreferences().getInt("index", 0)
    }

    private fun sharedPreferences() =
        NetEaseApplication.context.getSharedPreferences("before_ads", Context.MODE_PRIVATE)
}