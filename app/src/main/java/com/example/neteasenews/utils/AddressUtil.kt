package com.example.neteasenews.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import com.example.neteasenews.NetEaseApplication


@SuppressLint("MissingPermission")
fun getLatAndLng(block: (address: String) -> Unit) {
    val locationManager =
        NetEaseApplication.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
    if (location != null) {
        // 获取地址信息
        var address = getAddress(NetEaseApplication.context, location.latitude, location.longitude)
        if (address == null || address == "") {
            "获取位置信息失败".showToast()
            //默认显示北京的地址,北京市
            address = "北京"
            block(address)
        } else {
            block(address)
        }
    } else {
        "获取位置信息失败，请检查是够开启GPS,是否授权".showToast()
    }


}

private fun getAddress(context: Context, latitude: Double, longitude: Double): String {
    var cityName = ""
    try {
        val ge = Geocoder(context)
        var addressList = ge.getFromLocation(latitude, longitude, 1) as List<Address>
        if (addressList != null && addressList.isNotEmpty()) {
            for (address in addressList) {
                //去掉市这个字
                cityName = address.locality.substring(0, address.locality.length - 1)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return cityName;
}