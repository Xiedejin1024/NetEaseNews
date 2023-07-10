package com.example.neteasenews.utils

import android.text.TextUtils
import com.google.gson.Gson


object JsonUtil {

    var mGson: Gson? = null

    fun <T> JsonParse(string: String?, tClass: Class<T>): T? {
        if (TextUtils.isEmpty(string) || tClass == null) return null
        if (mGson == null) {
            mGson = Gson()
        }
        return mGson!!.fromJson(string, tClass)
    }
}
