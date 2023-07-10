package com.example.neteasenews.ui.home.headline

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.neteasenews.NetEaseApplication
import com.example.neteasenews.R
import com.example.neteasenews.logic.model.Ads


class BannerAdapter() : PagerAdapter() {

    private lateinit var mAds: List<Ads>
    private lateinit var mView: List<View>

    constructor(bannerList: ArrayList<Ads>, viewList: ArrayList<View>) : this(){
        mAds = bannerList
        mView = viewList
    }


    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val index = position % mView.size
        val view = mView[index]
        val mIcon = view.findViewById<ImageView>(R.id.image_icon)
        val imgSrc: String = mAds[index].imgsrc
        Glide.with(NetEaseApplication.context).load(imgSrc).into(mIcon)
        container.addView(view)
        return view
    }

    fun upAdapterData(bannerList: List<Ads>, viewList: ArrayList<View>) {
        mAds = bannerList
        mView = viewList
        notifyDataSetChanged()
    }

}