package com.example.neteasenews.ui.common.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.example.neteasenews.R
import com.example.neteasenews.event.MessageEvent
import com.example.neteasenews.utils.ActivityCollector
import com.example.neteasenews.utils.LogUtil
import com.gyf.immersionbar.ImmersionBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

open class BaseActivity : AppCompatActivity() {


    /**
     * 当前Activity的实例。
     */
    protected var activity: Activity? = null

    /** 当前Activity的弱引用，防止内存泄露  */
    private var activityWR: WeakReference<Activity>? = null

    //日志输出标志

    protected val TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.v(TAG, "BaseActivity-->onCreate()")
        activity = this
        activityWR = WeakReference(activity!!)
        ActivityCollector.pushTask(activityWR)
        EventBus.getDefault().register(this)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.v(TAG, "BaseActivity-->onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtil.v(TAG, "BaseActivity-->onRestoreInstanceState()")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtil.v(TAG, "BaseActivity-->onNewIntent()")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.v(TAG, "BaseActivity-->onRestart()")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.v(TAG, "BaseActivity-->onStart()")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.v(TAG, "BaseActivity-->onResume()")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.v(TAG, "BaseActivity-->onPause()")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.v(TAG, "BaseActivity-->onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.v(TAG, "BaseActivity-->onDestroy()")
        activity = null
        ActivityCollector.removeTask(activityWR)
        EventBus.getDefault().unregister(this)

    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        setStatusBarBackground(R.color.colorPrimaryDark)
        setupViews()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setStatusBarBackground(R.color.colorPrimaryDark)
        setupViews()
    }

    protected open fun setupViews() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent: MessageEvent) {

    }


    /**
     * 设置状态栏背景色
     */
    open fun setStatusBarBackground(@ColorRes statusBarColor: Int) {
        ImmersionBar.with(this).autoStatusBarDarkModeEnable(true, 0.2f)
            .statusBarColor(statusBarColor).fitsSystemWindows(true).init()
    }


}