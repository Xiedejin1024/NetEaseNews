package com.example.neteasenews.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.OnFocusChangeListener
import android.webkit.JavascriptInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neteasenews.databinding.ActivityDetailBinding
import com.example.neteasenews.logic.model.splash.DownloadImageService
import com.example.neteasenews.ui.detail.popuwindow.CustomPopupWindow
import com.example.neteasenews.ui.freed.FreedBackActivity
import com.example.neteasenews.ui.freed.FreedBacks
import com.example.neteasenews.ui.freed.HotPost
import com.example.neteasenews.ui.freed.HotPostAdapter
import com.example.neteasenews.utils.JsonUtil
import com.example.neteasenews.utils.LogUtil
import com.example.neteasenews.utils.showToast
import org.json.JSONException
import org.json.JSONObject


class DetailActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this)[DetailViewModel::class.java] }

    private var _binding: ActivityDetailBinding? = null

    private val binding: ActivityDetailBinding
        get() = _binding!!

    private lateinit var docId: String

    lateinit var imgList: ArrayList<DetailImage>
    lateinit var allHotPost: ArrayList<FreedBacks>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allHotPost = ArrayList<FreedBacks>()
        initView()

        docId = intent.getStringExtra(DOCID).toString()
        viewModel.getDetailData(docId)
        viewModel.getFreedBackData(docId)
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    private fun initView() {
        //webview内的点击事件
        binding.webviewDetail.settings.javaScriptEnabled = true
        binding.webviewDetail.addJavascriptInterface(this, "demo")

        binding.imageBack.setOnClickListener {
            finish()
        }

        binding.imageMoreFunction.setOnClickListener {
            //从底部跳出popuwindow功能
            val sharedPopupWindow = CustomPopupWindow.sharedPopupWindow(this, true)
            sharedPopupWindow.showAtLocation(binding.root, Gravity.BOTTOM, 0, 0)


        }

        binding.tvReplyNumber.setOnClickListener {
            //从底部跳出popuwindow功能
            val commentdPopupWindow = CustomPopupWindow.commentPopupWindow(this, allHotPost)
            commentdPopupWindow.showAtLocation(binding.root, Gravity.BOTTOM, 0, 0)
        }

        binding.tvShareDetail.setOnClickListener {
            //从底部跳出popuwindow功能
            val sharedPopupWindow = CustomPopupWindow.sharedPopupWindow(this, false)
            sharedPopupWindow.showAtLocation(binding.root, Gravity.BOTTOM, 0, 0)
        }

        viewModel.detailLiveData.observe(this, Observer { result ->
            val responseStr = result.getOrNull()
            if (responseStr != null) {
                try {
                    val json = JSONObject(responseStr)
                    val docIdJson = json.getJSONObject(docId)
                    val docDetail =
                        JsonUtil.JsonParse(docIdJson.toString(), DocDetail::class.java)!!
                    imgList = docDetail.img as ArrayList<DetailImage>
                    var docDetailBody = docDetail.body
                    for (i in imgList.indices) {
                        val imgHTML =
                            "<IMG src='" + docDetail.img[i].src + "'onClick='showImage()'/>"
                        docDetailBody =
                            docDetailBody.replaceFirst("<!--IMG#$i-->".toRegex(), imgHTML)
                    }
                    var titleHTML =
                        "<p><span style='font-size:28px;'><strong>" + docDetail.title + "</strong></span></p>"
                    titleHTML =
                        titleHTML + "<p><span style='color:#666666;'>" + docDetail.source + "&nbsp&nbsp" + docDetail.ptime + "</span></p>"

                    docDetailBody = titleHTML + docDetailBody
                    docDetailBody = "<html><head><style>img{width:100%}</style>" +
                            "<script type=\'text/javascript\'>function showImage(){window.demo.imageClick()}</script></head><body>" +
                            "<span style='font-size:19px;'>" + docDetailBody + "</span>" + "</body></html>"

                    binding.webviewDetail.loadDataWithBaseURL(
                        null,
                        docDetailBody,
                        "text/html",
                        "utf-8",
                        null
                    )
                    binding.tvReplyNumber.text = docDetail.replyCount.toString()
                    binding.tvVotecount.text = docDetail.threadVote.toString()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else {
                "数据请求失败".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.freedBacksLiveData.observe(this, Observer { result ->
            val responseStr = result.getOrNull()
            if (responseStr != null) {
                try {
                    val json = JSONObject(responseStr)
                    val hotPosts = json.optJSONArray("hotPosts")

                    for (i in 0 until hotPosts.length()) {
                        val tmp = hotPosts.getJSONObject(i)
                        val key = tmp.keys()
                        val freedBacks = FreedBacks()
                        while (key.hasNext()) {
                            val next = key.next()
                            val jsonObject = tmp.getJSONObject(next)
                            val hotPost =
                                JsonUtil.JsonParse(jsonObject.toString(), HotPost::class.java)

                            if (hotPost != null) {
                                freedBacks.addFreedback(hotPost)
                            }
                        }
                        allHotPost.add(freedBacks)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else {
                "数据请求失败".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }


    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    fun imageClick() {
        val intent = Intent(this, DetailImageActivity::class.java)
        intent.putExtra(DetailImageActivity.IMAGEURL, imgList)
        startActivity(intent)
    }

    companion object {
        const val DOCID: String = "docid"
    }

}

