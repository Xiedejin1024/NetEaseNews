package com.example.neteasenews.ui.mine

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.neteasenews.R


class MineItemCustom(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private var mTv_MineDesc: TextView? = null
    private var mTv_MineText: TextView? = null
    private var mImageMineView: ImageView? = null
    private var mRlMineView: RelativeLayout? = null

    init {
        initView(context)
        initData(attrs)
    }


    private fun initData(attrs: AttributeSet) {
        val text = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "text")
        val desc = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "desc")
        val iconLeft =
            attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "iconLeft")
        if (!TextUtils.isEmpty(iconLeft)) {
            val drawableLeft = resources.getDrawable(iconLeft.substring(1).toInt())
            mTv_MineText!!.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
            mTv_MineText!!.compoundDrawablePadding = 10
        }
        mTv_MineText!!.text = text
        mTv_MineDesc!!.text = desc
        val bgselector =
            attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "bgselector")
        when (bgselector) {
            "0" -> mRlMineView!!.setBackgroundResource(R.drawable.selector_item_mine_frist)
            "1" -> mRlMineView!!.setBackgroundResource(R.drawable.selector_item_mine_middle)
            "2" -> mRlMineView!!.setBackgroundResource(R.drawable.selector_item_mine_last)
        }
    }

    private fun initView(context: Context) {
        val view = inflate(context, R.layout.item_mine_view, this)
        mRlMineView = findViewById<RelativeLayout>(R.id.rl_item_main_view)
        mTv_MineDesc = findViewById<TextView>(R.id.tv_item_main_view_desc)
        mTv_MineText = findViewById<TextView>(R.id.tv_item_main_view_text)
        mImageMineView = findViewById<ImageView>(R.id.image_item_main_view)
        mImageMineView = findViewById<ImageView>(R.id.image_item_main_view)
    }
}