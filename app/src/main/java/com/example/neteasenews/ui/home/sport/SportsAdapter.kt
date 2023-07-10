package com.example.neteasenews.ui.home.sport

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.neteasenews.NetEaseApplication
import com.example.neteasenews.R
import com.example.neteasenews.logic.model.SportsDetail


class SportsAdapter(context: SportsFragment) : BaseAdapter() {

    private var datas: ArrayList<SportsDetail>
    private var mContext: SportsFragment

    init {
        mContext = context
        datas = ArrayList<SportsDetail>()
    }


    override fun getCount(): Int {
        return if (datas != null) datas.size else 0
    }

    override fun getItem(position: Int): Any? {
        return datas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, converView: View?, viewGroup: ViewGroup?): View? {
        var converView = converView
        var holder: ViewHolder? = null
        if (converView == null) {
            holder = ViewHolder()
            converView =
                View.inflate(NetEaseApplication.context, R.layout.item_fragment_headline, null)
            holder.imageView = converView.findViewById<ImageView>(R.id.image_headline_icon)
            holder.tv_title = converView.findViewById<TextView>(R.id.tv_headline_title)
            holder.tv_source = converView.findViewById<TextView>(R.id.tv_headline_source)
            holder.tv_replyCount = converView.findViewById<TextView>(R.id.tv_headline_replyCount)
            converView.tag = holder
        } else {
            holder = converView.tag as ViewHolder
        }

        val sportsDetail = datas[position]
        Glide.with(NetEaseApplication.context).load(sportsDetail.imgsrc).into(holder.imageView)
        holder!!.tv_title!!.text = sportsDetail.title
        holder.tv_source!!.text = sportsDetail.source
        holder.tv_replyCount!!.text = sportsDetail.replyCount.toString() + "跟贴"
        return converView
    }

    fun upAdapterData(data: List<SportsDetail>) {
        datas.clear()
        datas.addAll(data)
        notifyDataSetChanged()
    }


    internal class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var tv_title: TextView
        lateinit var tv_source: TextView
        lateinit var tv_replyCount: TextView
    }
}