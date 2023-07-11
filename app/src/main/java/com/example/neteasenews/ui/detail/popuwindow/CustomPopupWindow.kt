package com.example.neteasenews.ui.detail.popuwindow

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neteasenews.NetEaseApplication
import com.example.neteasenews.R
import com.example.neteasenews.ui.freed.FreedBacks
import com.example.neteasenews.ui.freed.HotPostAdapter
import com.example.neteasenews.ui.home.HeadTitleAdapter
import com.example.neteasenews.ui.home.HomeFragment
import com.example.neteasenews.ui.home.HomeFragmentViewModel
import com.example.neteasenews.utils.showToast

@SuppressLint("StaticFieldLeak")
object CustomPopupWindow : AdapterView.OnItemClickListener {

    lateinit var adapter: HeadTitleAdapter
    lateinit var addAdapter: HeadTitleAdapter
    lateinit var hideTitle: Array<String>

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

    fun headlineTitlePopupWindow(
        context: Context,
        titleName: Array<String>,
        viewModel: HomeFragmentViewModel,
        lastContent: String?,
        noShowContent: String?
    ): PopupWindow {
        val view = LayoutInflater.from(context).inflate(R.layout.popupwindow_headline_title, null)
        val popupWindow = PopupWindow(context).apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
            // 设置背景
            setBackgroundDrawable(ColorDrawable(NetEaseApplication.context.resources.getColor(R.color.transparency)))

            val gridView = view.findViewById<GridView>(R.id.gridview)
            adapter = HeadTitleAdapter(context, titleName)
            gridView.adapter = adapter

            val addGridView = view.findViewById<GridView>(R.id.add_gridview)
            if (noShowContent != null) {
                hideTitle = noShowContent.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                addAdapter = HeadTitleAdapter(context, hideTitle)
            } else {
                addAdapter = HeadTitleAdapter(context)
            }
            addGridView.adapter = addAdapter

            // 设置界面
            contentView = view
            // true时界面可点
            isTouchable = true
            // true时PopupWindow处理返回键
            isFocusable = true
            // true时点击外部消失，如果touchable为false时，点击界面也消失
            isOutsideTouchable = true
            animationStyle = R.style.popup_window_animation
            view.findViewById<ImageView>(R.id.iv_back_title).setOnClickListener {
                dismiss()
            }

            val editTitle = view.findViewById<TextView>(R.id.tv_edit_title)
            editTitle.setOnClickListener {
                adapter.setDelect()
                if (editTitle.text == "完成") {
                    viewModel.saveHomeTitle(adapter.getContent())
                    viewModel.saveHideHomeTitle(addAdapter.getContent())
                    //通知刷新界面
                    viewModel.refreshHeadlineUI(true)
                    editTitle.text = "编辑"
                } else {
                    if (adapter.isShowDelect()) editTitle.text = "完成"
                    else editTitle.text = "编辑"
                }
            }

            gridView.onItemClickListener = this@CustomPopupWindow
            addGridView.onItemClickListener = this@CustomPopupWindow

        }
        return popupWindow
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (parent.id) {
            R.id.gridview -> {//判断是否是删除状态
                if (position == 0) {
                    "头条不能删除".showToast()
                    return
                }
                if (adapter.isShowDelect()) {
                    val removeItem = adapter.removeItem(position)
                    addAdapter.addItem(removeItem)
                }
            }

            R.id.add_gridview -> {
                if (adapter.isShowDelect()) {
                    val removeItem = addAdapter.removeItem(position)
                    adapter.addItem(removeItem)
                }
            }

        }
    }

}