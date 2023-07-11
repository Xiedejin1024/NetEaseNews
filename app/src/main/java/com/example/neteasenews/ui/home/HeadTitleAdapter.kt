package com.example.neteasenews.ui.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.example.neteasenews.R


class HeadTitleAdapter() : BaseAdapter() {
    private lateinit var mContext: Context
    private lateinit var titleList: ArrayList<String>
    private var isShowDel = false


    constructor(context: Context) : this() {
        mContext = context
        titleList = ArrayList<String>()
    }

    constructor(context: Context, titleName: Array<String>) : this() {
        mContext = context
        titleList = ArrayList<String>()
        titleList.addAll(titleName)

    }

    override fun getCount(): Int {
        return if (titleList != null) titleList.size else 0
    }

    override fun getItem(position: Int): Any {
        return titleList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var converview = convertView
        if (converview == null) {
            converview = View.inflate(mContext, R.layout.item_headline_title, null)
        }
        val mTvTitle = converview!!.findViewById<TextView>(R.id.tv_headline_title_name)
        val mImageDel = converview.findViewById<ImageView>(R.id.image_delect)
        mTvTitle.text = titleList[position]
        if (titleList[position] == "头条") {
            mImageDel.visibility = View.GONE
        } else {
            mImageDel.visibility = if (isShowDel) View.VISIBLE else View.GONE
        }
        return converview
    }

    fun isShowDelect(): Boolean {
        return isShowDel
    }

    fun setDelect() {
        isShowDel = !isShowDel
        notifyDataSetChanged()
    }


    fun removeItem(position: Int): String {
        var name = ""
        if (position >= 0 && position <= titleList.size - 1) {
            name = titleList[position]
            titleList.remove(name)
            notifyDataSetChanged()
        }
        return name
    }

    fun getContent(): String {
        val sb = StringBuilder()
        for (i in 0 until titleList.size) {
            val string: String = titleList[i]
            sb.append(string)
            if (i != titleList.size - 1) {
                sb.append("-")
            }
        }
        return sb.toString()
    }


    fun addItem(removeItem: String) {
        titleList.add(removeItem)
        notifyDataSetChanged()
    }
}