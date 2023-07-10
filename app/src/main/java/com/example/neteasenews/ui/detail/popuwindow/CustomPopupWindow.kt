package com.example.neteasenews.ui.detail.popuwindow

import android.content.Context
import android.content.res.Resources.Theme
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neteasenews.NetEaseApplication
import com.example.neteasenews.R
import com.example.neteasenews.ui.freed.FreedBacks
import com.example.neteasenews.ui.freed.HotPostAdapter
import com.example.neteasenews.utils.LogUtil

object CustomPopupWindow {

    fun sharedPopupWindow(context: Context, showMoreFunction: Boolean): PopupWindow {

        val popupWindow = PopupWindow(context).apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            // 设置背景
            setBackgroundDrawable(ColorDrawable(NetEaseApplication.context.resources.getColor(R.color.transparency)))
            val view = LayoutInflater.from(context).inflate(R.layout.popupwindow_shared, null)
            val moreLayout = view.findViewById<LinearLayout>(R.id.ll_more_layout)

            if (showMoreFunction) moreLayout.visibility = View.VISIBLE
            else moreLayout.visibility = View.GONE

            // 设置界面
            contentView = view
            // true时界面可点
            isTouchable = true
            // true时PopupWindow处理返回键
            isFocusable = true
            // true时点击外部消失，如果touchable为false时，点击界面也消失
            isOutsideTouchable = true
            animationStyle = R.style.popup_window_animation

        }
        return popupWindow
    }

    fun commentPopupWindow(context: Context, allHotPost: ArrayList<FreedBacks>): PopupWindow {

        val popupWindow = PopupWindow(context).apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            val hScreen = context.resources.displayMetrics.heightPixels
            height = (hScreen / 2) + (hScreen / 4)

            // 设置背景
            setBackgroundDrawable(ColorDrawable(NetEaseApplication.context.resources.getColor(R.color.transparency)))
            val view = LayoutInflater.from(context).inflate(R.layout.popupwindow_comment, null)
            val recycleView = view.findViewById<RecyclerView>(R.id.comment_recyclerview)
            //设置RecyclerView
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recycleView.layoutManager = layoutManager
            var adapter = HotPostAdapter(allHotPost)
            recycleView.adapter = adapter


            // 设置界面
            contentView = view
            // true时界面可点
            isTouchable = true
            // true时PopupWindow处理返回键
            isFocusable = true
            // true时点击外部消失，如果touchable为false时，点击界面也消失
            isOutsideTouchable = true
            animationStyle = R.style.popup_window_animation

        }

        return popupWindow
    }

    fun headlineTitlePopupWindow(context: Context): PopupWindow {
        val view = LayoutInflater.from(context).inflate(R.layout.popupwindow_headline_title, null)


        val popupWindow = PopupWindow(context).apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
            // 设置背景
            setBackgroundDrawable(ColorDrawable(NetEaseApplication.context.resources.getColor(R.color.transparency)))


            // 设置界面
            contentView = view
            // true时界面可点
            isTouchable = true
            // true时PopupWindow处理返回键
            isFocusable = true
            // true时点击外部消失，如果touchable为false时，点击界面也消失
            isOutsideTouchable = true
            animationStyle = R.style.popup_window_animation

        }

        return popupWindow
    }

}