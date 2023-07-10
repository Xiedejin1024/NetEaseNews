package com.example.neteasenews.logic

import androidx.lifecycle.liveData
import com.example.neteasenews.logic.dao.SplashDao
import com.example.neteasenews.logic.model.Headline
import com.example.neteasenews.logic.model.HotsDetail
import com.example.neteasenews.logic.model.Native
import com.example.neteasenews.logic.model.NativeDetail
import com.example.neteasenews.logic.model.Sports
import com.example.neteasenews.logic.model.SportsDetail
import com.example.neteasenews.ui.freed.FreedBacks
import com.example.neteasenews.ui.freed.HotPost
import com.example.neteasenews.utils.LogUtil
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

//统一的仓库管理
object Repository {

    /**启动页部分*/
    fun saveAds(adsString: String) = SplashDao.saveAds(adsString)

    fun getSaveAds(): String? = SplashDao.getSaveAds()

    fun saveLastTime(lastTime: Long) = SplashDao.saveLastTime(lastTime)
    fun getLastTime(): Long = SplashDao.getLastTime()

    fun saveTimeOut(timeOut: Int) = SplashDao.saveTimeOut(timeOut)
    fun getTimeOut(): Int = SplashDao.getTimeOut()

    fun saveIndex(index: Int) = SplashDao.saveIndex(index)
    fun getIndex(): Int = SplashDao.getIndex()


    fun searchHeadline(headline: Headline) = liveData(Dispatchers.IO) {
        val result = try {
            val hotsDetail = headline.T1348647909107
            Result.success(hotsDetail)
        } catch (e: Exception) {
            Result.failure<List<HotsDetail>>(e)
        }
        emit(result)
    }

    fun searchDetailByDocId(string: String) = liveData(Dispatchers.IO) {
        val result = try {
            Result.success(string)
        } catch (e: Exception) {
            Result.failure<String>(e)
        }
        emit(result)
    }

    fun searchFreedBackByDocId(string: String) = liveData(Dispatchers.IO) {
        val result = try {
            Result.success(string)
        } catch (e: Exception) {
            Result.failure<String>(e)
        }
        emit(result)
    }

    fun searchSports(sports: Sports) = liveData(Dispatchers.IO) {
        val result = try {
            val sportsDetail = sports.T1348649079062
            Result.success(sportsDetail)
        } catch (e: Exception) {
            Result.failure<List<SportsDetail>>(e)
        }
        emit(result)
    }

    fun searchNative(native: Native) = liveData(Dispatchers.IO) {
        val result = try {
            val nativeDetail = native.T1348648650048
            Result.success(nativeDetail)
        } catch (e: Exception) {
            Result.failure<List<NativeDetail>>(e)
        }
        emit(result)
    }

}