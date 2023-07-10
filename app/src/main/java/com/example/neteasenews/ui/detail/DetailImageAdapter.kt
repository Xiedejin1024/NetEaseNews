package com.example.neteasenews.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.neteasenews.NetEaseApplication
import com.github.chrisbanes.photoview.PhotoView


class DetailImageAdapter(photoview: ArrayList<PhotoView>, dataList: ArrayList<DetailImage>) :
    PagerAdapter() {
    private var photoView: ArrayList<PhotoView>
    private var detailData: ArrayList<DetailImage>

    init {
        photoView = photoview
        detailData = dataList
        photoView.addAll(photoview)
        detailData.addAll(dataList)
    }


    override fun getCount(): Int {
        return photoView.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val photoView = PhotoView(container.context)
        Glide.with(NetEaseApplication.context).load(detailData[position].src).into(photoView)
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return photoView
    }

}