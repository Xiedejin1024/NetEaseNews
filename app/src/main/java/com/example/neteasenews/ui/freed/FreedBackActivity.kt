package com.example.neteasenews.ui.freed

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.neteasenews.R
import com.example.neteasenews.logic.network.HttpResponse
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference


class FreedBackActivity : AppCompatActivity() {
    companion object {
        val DOCID: String = "docid"
    }

   /* val DOCID = "docid"
    private val INIT_LIST = 1
    private var mHttputil: Httputil? = null
    private var mMyhandler: MyHandler? = null
    private var mPostList: ArrayList<FreedBacks>? = null
    private var mLvFreedBack: ListView? = null
    private var mAdapter: FreedBacksAdapter? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freedback)
       /* mLvFreedBack = findViewById<ListView>(R.id.lv_freedback)
        mMyhandler = MyHandler(this)
        mPostList = ArrayList<FreedBacks>()
        val docid = intent.getStringExtra(DOCID)
        getFreedBack(docid)*/
    }

   /* private fun getFreedBack(docid: String?) {
        val freedbackURL: String = Contance.getFreedbackURL(docid)
        mHttputil = Httputil.getInstance()
        mHttputil.doGet(freedbackURL, object : HttpResponse<String?>(
            String::class.java
        ) {
            override fun onError() {}
            fun onSuccess(string: String) {
                try {
                    val json = JSONObject(string)
                    val hotPosts = json.optJSONArray("hotPosts") ?: return
                    *//*FreedBacks title = new FreedBacks();
                    title.setTitle(true);
                    title.setTitle("热门跟帖");
                    mPostList.add(title);*//*for (i in 0 until hotPosts.length()) {
                        val freedBacks = FreedBacks()
                        val tmp = hotPosts.getJSONObject(i)
                        val key = tmp.keys()
                        while (key.hasNext()) {
                            val next = key.next()
                            val jsonObject = tmp.getJSONObject(next) ?: continue
                            val back: FreedBack =
                                Jsonutil.Jsonparse(jsonObject.toString(), FreedBack::class.java)
                                    ?: continue
                            back.setIndex(Integer.valueOf(next))
                            freedBacks.addFreedback(back)
                        }
                        freedBacks.sort()
                        mPostList!!.add(freedBacks)
                    }
                    mMyhandler.sendEmptyMessage(INIT_LIST)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

    internal class MyHandler(activity: FreedBackActivity) : Handler() {
        //采用弱引用，避免内存泄露
        private val activity: WeakReference<FreedBackActivity>

        init {
            this.activity = WeakReference(activity)
        }

        fun handleMessage(msg: Message) {
            val freedBackActivity = activity.get() ?: return
            when (msg.what) {
                INIT_LIST -> freedBackActivity.showAdapter()
            }
        }
    }

    private fun showAdapter() {
        mAdapter = FreedBacksAdapter(this, mPostList)
        mLvFreedBack!!.adapter = mAdapter
    }*/
}