package com.example.neteasenews.logic.model.splash

import android.app.IntentService
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.neteasenews.logic.network.UrlContance
import com.example.neteasenews.utils.LogUtil
import com.example.neteasenews.utils.MD5util
import java.io.File
import java.io.FileOutputStream
import java.net.URL


@Suppress("DEPRECATION")
class DownloadImageService : IntentService("DownloadImageService") {

    override fun onHandleIntent(intent: Intent?) {
        val allAds = intent?.getSerializableExtra(SPLASH_DATAS) as Ads
        val adsDetails = allAds!!.ads
        for (i in adsDetails.indices) {
            val detail = adsDetails[i]
            val materialList = detail.materialList[0]//获取图片的列表即可
            val imagerUrl = materialList.urls[0]

            //采用md5生成本地唯一的文件名称
            val imageName = MD5util.md52Str(imagerUrl)
            if (!imagerDownLoaded(imageName)) {//检擦没有下载,就进行下载功能
                saveImage(imagerUrl, imageName)
            }
        }
    }

    private fun saveImage(imagerUrl: String, imageName: String) {
        try {
            val connection = URL(imagerUrl).openConnection()
            val bitmap = BitmapFactory.decodeStream(connection.getInputStream())
            if (bitmap != null) {
                val path = File(applicationContext.cacheDir, UrlContance.CHILDFILE)
                if (!path.exists()) {
                    path.mkdirs()
                }
                val image = File(path, "$imageName.jpg")
                if (image.exists()) {
                    return
                }

                val fos = FileOutputStream(image)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos)
                fos.flush()
                fos.close()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun imagerDownLoaded(imageName: String): Boolean {
        val image = getImageByName(imageName) ?: return false
        val bitmap = BitmapFactory.decodeFile(image.absolutePath) ?: return false
        bitmap.recycle()
        return true
    }

    private fun getImageByName(imageName: String): File? {
        val path = File(applicationContext.cacheDir, UrlContance.CHILDFILE)
        if (!path.exists()) {
            return null
        }
        val image = File(path, "$imageName.jpg")
        return if (!image.exists()) {
            null
        } else image
    }

    companion object {
        var SPLASH_DATAS = "datas"
    }

}
