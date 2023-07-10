package com.example.neteasenews.ui.home.sport

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.neteasenews.R
import com.example.neteasenews.databinding.FragmentHeadlineBinding
import com.example.neteasenews.databinding.FragmentSportBinding
import com.example.neteasenews.ui.detail.DetailActivity
import com.example.neteasenews.ui.home.headline.HeadlineAdapter
import com.example.neteasenews.utils.LogUtil
import com.example.neteasenews.utils.showToast


class SportsFragment : Fragment(), AbsListView.OnScrollListener {

    val viewModel by lazy { ViewModelProvider(requireActivity())[SportsViewModel::class.java] }

    private var _binding: FragmentHeadlineBinding? = null
    val binding get() = _binding!!
    private var loadMoreIndex = 0
    private lateinit var adapter: SportsAdapter
    private var isScrollToBottom = false
    private var isScrollToTop = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingData(true)

    }

    private fun loadingData(firstLoading: Boolean) {
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
        binding.headlineListview.emptyView = binding.root.findViewById(R.id.loading)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = SportsAdapter(this)
        binding.headlineListview.adapter = adapter
        binding.headlineListview.setOnScrollListener(this)

        viewModel.sportsLiveData.observe(viewLifecycleOwner, Observer { result ->
            val hotsDetail = result.getOrNull()
            if (hotsDetail != null) {
                viewModel.sportDetailList.addAll(hotsDetail)
                adapter.upAdapterData(viewModel.sportDetailList)
                binding.swipeRefresh.isRefreshing = false
                loadMoreIndex++;//刷新页面成功的次数，用以分页查询的

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
            viewModel.sportDetailList.clear()
            loadingData(true)
        }

        binding.headlineListview.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->

            val hotsDetail = viewModel.sportDetailList[position]
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

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isScrollToBottom) {
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


}