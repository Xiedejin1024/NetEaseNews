package com.example.neteasenews.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object MD5util {
    private var mDigest: MessageDigest? = null

    init {
        try {
            mDigest = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    /*
     * 对key进行MD5加密，如果没有MD5加密算法，直接使用key对于的hash值
     * */
    fun str2MD5(key: String): String {
        if (mDigest == null) {
            return key.hashCode().toString()
        }
        mDigest!!.update(key.toByteArray())
        return bytes2HexString(mDigest!!.digest())
    }

    private fun bytes2HexString(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (i in bytes.indices) {
            val hexString = Integer.toHexString(bytes[i].toInt() and 0xff)
            if (hexString.length == 1) {
                sb.append("0")
            }
            sb.append(hexString)
        }
        return sb.toString()
    }

    fun md52Str(md5: String): String {
        val array = mDigest!!.digest(md5.toByteArray())
        val sb = StringBuilder()
        for (i in array.indices) {
            sb.append(Integer.toHexString(array[i].toInt() and 0xff or 0x100).substring(1, 3))
        }
        return sb.toString()
    }
}
