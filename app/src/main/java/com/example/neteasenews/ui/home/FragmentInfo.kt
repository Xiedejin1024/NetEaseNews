package com.example.neteasenews.ui.home

import androidx.fragment.app.Fragment

class FragmentInfo(mFragment: Fragment, titleName: String) {

    var fragments: Fragment
    var mName: String

    init {
        fragments = mFragment
        mName = titleName
    }


    fun getFragment(): Fragment {
        return fragments
    }

    fun setFragment(fragment: Fragment) {
        this.fragments = fragment
    }

    fun getName(): String {
        return mName
    }

    fun setName(name: String) {
        this.mName = name
    }

}