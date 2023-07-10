package com.example.neteasenews.ui.home.headline

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.neteasenews.R
import com.example.neteasenews.databinding.FragmentHeadlineBinding
import com.example.neteasenews.ui.detail.DetailActivity
import com.example.neteasenews.ui.detail.popuwindow.CustomPopupWindow
import com.example.neteasenews.utils.LogUtil
import com.example.neteasenews.utils.showToast


class HeadlineFragment : Fragment(), AbsListView.OnScrollListener, ViewPager.OnPageChangeListener {

    private val viewModel by lazy { ViewModelProvider(requireActivity())[HeadlineViewModel::class.java] }
    private var _binding: FragmentHeadlineBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HeadlineAdapter
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var mLlDots: LinearLayout
    private lateinit var mBannerTitle: TextView
    private var isScrollToBottom = false
    private var isScrollToTop = true
    private var isFirstLoading = true
    private var loadMoreIndex = 0
    private val mDots = ArrayList<ImageView>()
    private val viewList = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingData(true)

    }

    private fun loadingData(firstLoading: Boolean) {
        isFirstLoading = firstLoading
        if (firstLoading) {
            loadMoreIndex = 0;
        }
        val start = loadMoreIndex * 20;
        val end = start + 20;
        viewModel.getHeadlineData(start, end)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHeadlineBinding.inflate(layoutInflater, container, false)

        val headView = inflater.inflate(R.layout.include_listview_viewpager, null)
        binding.headlineListview.addHeaderView(headView)
        viewPager = headView.findViewById<ViewPager>(R.id.head_viewpager)
        mLlDots = headView.findViewById<LinearLayout>(R.id.dots)
        mBannerTitle = headView.findViewById<TextView>(R.id.tv_viewpager_title)
        binding.headlineListview.emptyView = binding.root.findViewById(R.id.loading)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HeadlineAdapter(this)
        binding.headlineListview.adapter = adapter

        bannerAdapter = BannerAdapter(viewModel.bannerList, viewList)
        viewPager.adapter = bannerAdapter

        binding.headlineListview.setOnScrollListener(this)

        viewModel.headlineLiveData.observe(viewLifecycleOwner, Observer { result ->
            val hotsDetail = result.getOrNull()
            if (hotsDetail != null) {
                if (isFirstLoading) {
                    viewModel.bannerList.clear()
                    viewModel.hotsDetailList.clear()
                    //不可变-----可变： list.toMutableList()   集合类型的转换必须要用一个新的变量接收，要不则不能实现类型的转换
                    var hotsDetail2 = hotsDetail.toMutableList()
                    val adsList = hotsDetail[0].ads//取出轮播图
                    viewModel.bannerList.addAll(adsList)
                    hotsDetail2.removeAt(0);//删除轮播图位置
                    viewModel.hotsDetailList.addAll(hotsDetail2)
                    adapter.upAdapterData(viewModel.hotsDetailList)
                    //mMyHandler.sendEmptyMessage(INIT_SUCCESS)
                    initBannerViewPager()
                } else {

                    if (viewModel.hotsDetailList != null) {
                        viewModel.hotsDetailList.addAll(hotsDetail)
                        adapter.upAdapterData(viewModel.hotsDetailList)
                    }
                }
                binding.swipeRefresh.isRefreshing = false
                loadMoreIndex++//刷新页面成功的次数，用以分页查询的

            } else {
                "数据请求失败".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        binding.headlineListview.viewTreeObserver.addOnScrollChangedListener {
            if (binding.swipeRefresh != null) {
                binding.swipeRefresh.isEnabled = isScrollToTop

            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            loadingData(true)
        }

        binding.headlineListview.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            val head: Int = binding.headlineListview.headerViewsCount
            val hotsDetail = viewModel.hotsDetailList[position - head]
            if (!TextUtils.isEmpty(hotsDetail.postid)) {
                Intent(activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.DOCID, hotsDetail.docid)
                    startActivity(this)
                }

            } else {
                //跳转到专题界面
            }
        }

    }

    private fun initBannerViewPager() {
        viewList.clear()
        mLlDots.removeAllViews()
        mDots.clear()
        for (i in 0 until viewModel.bannerList.size) {
            val view = View.inflate(context, R.layout.item_banner_view, null)
            viewList.add(view)

            val dots = ImageView(context)
            dots.setImageResource(R.drawable.gray_banner_headline)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(10, 0, 0, 0)
            dots.layoutParams = params
            mLlDots.addView(dots)

            mDots.add(dots)

        }

        bannerAdapter.upAdapterData(viewModel.bannerList, viewList)
        viewPager.currentItem =
            ((Int.MAX_VALUE / 2) - (Int.MAX_VALUE / 2 % viewModel.bannerList.size))
        changedHotsState(0)
        viewPager.setOnPageChangeListener(this)

    }


    private fun changedHotsState(position: Int) {
        var position = position
        position %= mDots.size
        for (i in mDots.indices) {
            val imageView = mDots[i]
            if (position == i) {
                imageView.setImageResource(R.drawable.white_banner_headline)
            } else {
                imageView.setImageResource(R.drawable.gray_banner_headline)
            }
        }
        mBannerTitle.text = viewModel.bannerList[position].title
    }


    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
        if (scrollState == SCROLL_STATE_IDLE && isScrollToBottom) {
            //获取更多数据
            loadingData(false)
        }
    }

    override fun onScroll(
        view: AbsListView,
        firstVisibleItem: Int,
        visibleItemCount: Int,
        totalItemCount: Int
    ) {
        if (firstVisibleItem == 0) {
            val childAt = binding.headlineListview.getChildAt(0)
            isScrollToTop = childAt != null && childAt.top == 0
        }

        isScrollToBottom = view.lastVisiblePosition == (totalItemCount - 1)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        changedHotsState(position)
    }

    override fun onPageScrollStateChanged(state: Int) {}


}