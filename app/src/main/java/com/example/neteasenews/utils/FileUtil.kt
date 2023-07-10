package com.example.neteasenews.utils

import android.content.Context
import android.graphics.BitmapFactory
import com.example.neteasenews.logic.network.UrlContance
import java.io.File


object FileUtil {

    fun checkDownload(context: Context, imageName: String): Boolean {
        val image = getImageByName(context, imageName) ?: return false
        val bitmap = BitmapFactory.decodeFile(image.absolutePath) ?: return false
        bitmap.recycle()
        return true
    }

    fun getImageByName(context: Context, imageName: String): File? {
        val path = File(context.cacheDir, UrlContance.CHILDFILE)
        if (!path.exists()) return null
        val image = File(path, "$imageName.jpg")
        return if (!image.exists()) null else image
    }
}