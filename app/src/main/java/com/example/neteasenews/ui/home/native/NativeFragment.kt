package com.example.neteasenews.ui.home.sport

import android.Manifest
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
import com.example.neteasenews.R
import com.example.neteasenews.databinding.FragmentHeadlineBinding
import com.example.neteasenews.logic.network.UrlContance
import com.example.neteasenews.ui.detail.DetailActivity
import com.example.neteasenews.ui.home.native.NativeAdapter
import com.example.neteasenews.ui.home.native.NativeViewModel
import com.example.neteasenews.utils.LogUtil
import com.example.neteasenews.utils.getLatAndLng
import com.example.neteasenews.utils.showToast
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.ExplainReasonCallback
import com.permissionx.guolindev.callback.RequestCallback


class NativeFragment : Fragment(), AbsListView.OnScrollListener {

    val viewModel by lazy { ViewModelProvider(requireActivity())[NativeViewModel::class.java] }


    private var _binding: FragmentHeadlineBinding? = null
    val binding get() = _binding!!
    private var loadMoreIndex = 0
    private lateinit var adapter: NativeAdapter
    private var isScrollToBottom = false
    private var isScrollToTop = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingData(true)
        /* //申请权限获取当前定位信息
         PermissionX.init(this)
             .permissions(
                 Manifest.permission.ACCESS_COARSE_LOCATION,
                 Manifest.permission.ACCESS_FINE_LOCATION
             )
             .onExplainRequestReason(ExplainReasonCallback { scope, deniedList ->
                 scope.showRequestReasonDialog(
                     deniedList,
                     "PermissionX需要您同意以下权限才能正常使用",
                     "确定",
                     "取消"
                 )
             })
             .request(RequestCallback { allGranted, grantedList, deniedList ->
                 if (allGranted) {
                     getLatAndLng { address ->
                         //LogUtil.e("it520com", "address=${address}")
                         viewModel.nativeAddress = address
                     }
                 } else {
                     "您拒绝了如下权限：$deniedList".showToast()
                 }
                 loadingData(true)
             })*/
    }

    private fun loadingData(firstLoading: Boolean) {
        if (firstLoading) {
            loadMoreIndex = 0;
        }
        val start = loadMoreIndex * 20;
        val end = start + 20;
        viewModel.getNativeData(start, end)
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
        adapter = NativeAdapter(this)
        binding.headlineListview.adapter = adapter
        binding.headlineListview.setOnScrollListener(this)

        viewModel.nativeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val hotsDetail = result.getOrNull()
            if (hotsDetail != null) {
                viewModel.nativeDetailList.addAll(hotsDetail)
                adapter.upAdapterData(viewModel.nativeDetailList)
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
            viewModel.nativeDetailList.clear()
            loadingData(true)
        }

        binding.headlineListview.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->

            val hotsDetail = viewModel.nativeDetailList[position]
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