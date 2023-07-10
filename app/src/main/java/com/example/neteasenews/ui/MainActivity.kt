package com.example.neteasenews.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.neteasenews.R
import com.example.neteasenews.databinding.ActivityMainBinding
import com.example.neteasenews.event.MessageEvent
import com.example.neteasenews.event.RefreshEvent
import com.example.neteasenews.ui.common.ui.BaseActivity
import com.example.neteasenews.ui.community.CommunityFragment
import com.example.neteasenews.ui.home.HomeFragment
import com.example.neteasenews.ui.mine.MineFragment
import com.example.neteasenews.ui.video.VideoFragment
import org.greenrobot.eventbus.EventBus

class MainActivity : BaseActivity() {
    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private var _binding: ActivityMainBinding? = null

    private val binding: ActivityMainBinding
        get() = _binding!!

    private var backPressTime = 0L

    private var homePageFragment: HomeFragment? = null

    private var communityFragment: CommunityFragment? = null

    private var videoFragment: VideoFragment? = null

    private var mineFragment: MineFragment? = null

    private val fragmentManager: FragmentManager by lazy { supportFragmentManager }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun setupViews() {
        setOnClickListener(
            binding.navigationBar.btnHomePage,
            binding.navigationBar.btnCommunity,
            binding.navigationBar.btnVideo,
            binding.navigationBar.btnMine
        ) {
            when (this) {
                binding.navigationBar.btnHomePage -> {
                    notificationUiRefresh(0)
                    setTabSelection(0)
                }

                binding.navigationBar.btnCommunity -> {
                    notificationUiRefresh(1)
                    setTabSelection(1)
                }

                binding.navigationBar.btnVideo -> {
                    notificationUiRefresh(2)
                    setTabSelection(2)
                }

                binding.navigationBar.btnMine -> {
                    notificationUiRefresh(3)
                    setTabSelection(3)
                }
            }
        }

        setTabSelection(0)
    }

    /**
     * 批量设置控件点击事件。
     *
     * @param v 点击的控件
     * @param block 处理点击事件回调代码块
     */
    private fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
        val listener = View.OnClickListener { it.block() }
        v.forEach { it?.setOnClickListener(listener) }
    }

    private fun notificationUiRefresh(selectionIndex: Int) {
        when (selectionIndex) {
            0 -> {
                if (binding.navigationBar.ivHomePage.isSelected) EventBus.getDefault()
                    .post(RefreshEvent(HomeFragment::class.java))
            }

            1 -> {
                if (binding.navigationBar.ivCommunity.isSelected) EventBus.getDefault()
                    .post(RefreshEvent(CommunityFragment::class.java))
            }

            2 -> {
                if (binding.navigationBar.ivVideo.isSelected) EventBus.getDefault()
                    .post(RefreshEvent(VideoFragment::class.java))
            }

            3 -> {
                if (binding.navigationBar.ivMine.isSelected) EventBus.getDefault()
                    .post(RefreshEvent(MineFragment::class.java))
            }
        }
    }


    private fun setTabSelection(index: Int) {
        clearAllSelected()
        fragmentManager.beginTransaction().apply {
            hideFragments(this)
            when (index) {
                0 -> {
                    binding.navigationBar.ivHomePage.isSelected = true
                    binding.navigationBar.tvHomePage.isSelected = true
                    if (homePageFragment == null) {
                        homePageFragment = HomeFragment.newInstance()
                        add(R.id.homeActivityFragContainer, homePageFragment!!)
                    } else {
                        show(homePageFragment!!)
                    }
                }

                1 -> {
                    binding.navigationBar.ivCommunity.isSelected = true
                    binding.navigationBar.tvCommunity.isSelected = true
                    if (communityFragment == null) {
                        communityFragment = CommunityFragment.newInstance()
                        add(R.id.homeActivityFragContainer, communityFragment!!)
                    } else {
                        show(communityFragment!!)
                    }
                }

                2 -> {
                    binding.navigationBar.ivVideo.isSelected = true
                    binding.navigationBar.tvVideo.isSelected = true
                    if (videoFragment == null) {
                        videoFragment = VideoFragment.newInstance()
                        add(R.id.homeActivityFragContainer, videoFragment!!)
                    } else {
                        show(videoFragment!!)
                    }
                }

                3 -> {
                    binding.navigationBar.ivMine.isSelected = true
                    binding.navigationBar.tvMine.isSelected = true
                    if (mineFragment == null) {
                        mineFragment = MineFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mineFragment!!)
                    } else {
                        show(mineFragment!!)
                    }
                }

                else -> {
                    binding.navigationBar.ivHomePage.isSelected = true
                    binding.navigationBar.tvHomePage.isSelected = true
                    if (homePageFragment == null) {
                        homePageFragment = HomeFragment.newInstance()
                        add(R.id.homeActivityFragContainer, homePageFragment!!)
                    } else {
                        show(homePageFragment!!)
                    }
                }
            }
        }.commitAllowingStateLoss()
    }


    private fun clearAllSelected() {
        binding.navigationBar.ivHomePage.isSelected = false
        binding.navigationBar.tvHomePage.isSelected = false
        binding.navigationBar.ivCommunity.isSelected = false
        binding.navigationBar.tvCommunity.isSelected = false
        binding.navigationBar.ivVideo.isSelected = false
        binding.navigationBar.tvVideo.isSelected = false
        binding.navigationBar.ivMine.isSelected = false
        binding.navigationBar.tvMine.isSelected = false
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        transaction.run {
            if (homePageFragment != null) hide(homePageFragment!!)
            if (communityFragment != null) hide(communityFragment!!)
            if (videoFragment != null) hide(videoFragment!!)
            if (mineFragment != null) hide(mineFragment!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}