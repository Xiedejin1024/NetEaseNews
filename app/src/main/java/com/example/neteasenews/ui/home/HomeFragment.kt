package com.example.neteasenews.ui.home

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.neteasenews.NetEaseApplication
import com.example.neteasenews.R
import com.example.neteasenews.databinding.FragmentHomeBinding
import com.example.neteasenews.ui.detail.popuwindow.CustomPopupWindow
import com.example.neteasenews.ui.home.headline.HeadlineFragment
import com.example.neteasenews.ui.home.sport.NativeFragment
import com.example.neteasenews.ui.home.sport.SportsFragment
import com.example.neteasenews.utils.LogUtil
import com.gyf.immersionbar.ImmersionBar
import com.ogaclejapan.smarttablayout.SmartTabLayout


class HomeFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this)[HomeFragmentViewModel::class.java] }

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private var viewPager: ViewPager? = null

    private var tabLayout: SmartTabLayout? = null

    private var lastContent: String? = null
    private var noShowContent: String? = null

    private lateinit var arrayTitle: Array<String>

    lateinit var adapter: HeadlineFragmentAdapter

    private val mInfo = ArrayList<FragmentInfo>()

    private val arrayTitleName =
        NetEaseApplication.context.resources.getStringArray(R.array.headTitle)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ImmersionBar.with(this)
            .statusBarColor(R.color.colorTextPrimary)
            .fitsSystemWindows(true)
            .init()
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val showContent = viewModel.getHomeTitle()
        lastContent = showContent
        noShowContent = viewModel.getHideHomeTitle()

        if (TextUtils.isEmpty(showContent)) {
            arrayTitle = NetEaseApplication.context.resources.getStringArray(R.array.headTitle)
        } else {
            if (showContent != null) {
                arrayTitle =
                    showContent.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
        }
        for (i in arrayTitle.indices) {
            var fragmentInfo: FragmentInfo
            when (val titleName = arrayTitle[i]) {
                arrayTitleName[0] -> {
                    fragmentInfo = FragmentInfo(HeadlineFragment(), titleName)
                }

                arrayTitleName[1] -> {
                    fragmentInfo = FragmentInfo(SportsFragment(), titleName)
                }

                arrayTitleName[2] -> {
                    fragmentInfo = FragmentInfo(NativeFragment(), titleName)
                }

                else -> {
                    fragmentInfo = FragmentInfo(EmptyFragment(), titleName)
                }
            }
            mInfo.add(fragmentInfo)

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager
        tabLayout = binding.titleBar.tabLayout
        adapter = fragmentManager?.let { HeadlineFragmentAdapter(it, mInfo) }!!
        viewPager?.adapter = adapter
        tabLayout?.setViewPager(viewPager)

        binding.titleBar.ivEditedTitle.setOnClickListener {

            val headTitlePopupWindow = CustomPopupWindow.headlineTitlePopupWindow(
                requireContext(),
                arrayTitle,
                viewModel,
                lastContent,
                noShowContent
            )
            headTitlePopupWindow.showAsDropDown(
                binding.titleBar.ivEditedTitle,
                Gravity.BOTTOM,
                0,
                0
            )
        }
        viewModel.isRefreshFragment.observe(viewLifecycleOwner, Observer {
            lastContent = ""
            noShowContent = ""
            arrayTitle = emptyArray()
            val showContent = viewModel.getHomeTitle()
            lastContent = showContent
            noShowContent = viewModel.getHideHomeTitle()

            if (TextUtils.isEmpty(showContent)) {
                arrayTitle = NetEaseApplication.context.resources.getStringArray(R.array.headTitle)
            } else {
                if (showContent != null) {
                    arrayTitle = showContent.split("-".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                }
            }

            val mTitleInfo = ArrayList<FragmentInfo>()
            for (i in arrayTitle.indices) {
                var fragmentInfo: FragmentInfo
                when (val titleName = arrayTitle[i]) {
                    arrayTitleName[0] -> {
                        fragmentInfo = FragmentInfo(HeadlineFragment(), titleName)
                    }

                    arrayTitleName[1] -> {
                        fragmentInfo = FragmentInfo(SportsFragment(), titleName)
                    }

                    arrayTitleName[2] -> {
                        fragmentInfo = FragmentInfo(NativeFragment(), titleName)
                    }

                    else -> {
                        fragmentInfo = FragmentInfo(EmptyFragment(), titleName)
                    }
                }
                mTitleInfo.add(fragmentInfo)

            }
            adapter.upData(mTitleInfo)
            tabLayout?.setViewPager(viewPager)
        })

    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}