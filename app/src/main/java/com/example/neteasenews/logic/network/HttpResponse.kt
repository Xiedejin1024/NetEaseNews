package com.example.neteasenews.logic.network

import android.text.TextUtils
import com.example.neteasenews.utils.JsonUtil


abstract class HttpResponse<T>(var mTClass: Class<T>) {
    abstract fun onError()
    abstract fun onSuccess(t: T)


    fun parseData(json: String?) {

        if (TextUtils.isEmpty(json)) {
            onError()
            return
        }
        //如果想自己解析,就传入String类型
        if (mTClass == String::class.java) {
            onSuccess(json as T)
            return
        }
        val t: T? = JsonUtil.JsonParse(json, mTClass)
        if (t == null) {
            onError()
            return
        }
        onSuccess(t)
    }
}
