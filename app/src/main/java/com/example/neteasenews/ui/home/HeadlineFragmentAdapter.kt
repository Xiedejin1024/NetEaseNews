package com.example.neteasenews.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.Objects


class HeadlineFragmentAdapter : FragmentStatePagerAdapter {
    private var mInfo: ArrayList<FragmentInfo> = ArrayList<FragmentInfo>()


    constructor(fm: FragmentManager, fgInfo: List<FragmentInfo>) : super(fm) {
        mInfo.addAll(fgInfo)
    }

    override fun getItem(position: Int): Fragment {
        return mInfo[position].getFragment()
    }

    override fun getCount(): Int {
        return mInfo.size
    }

    override fun getPageTitle(position: Int): String {
        return mInfo[position].getName()
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun upData(mNewInfo: ArrayList<FragmentInfo>) {
        mInfo = mNewInfo
        notifyDataSetChanged()
    }

}