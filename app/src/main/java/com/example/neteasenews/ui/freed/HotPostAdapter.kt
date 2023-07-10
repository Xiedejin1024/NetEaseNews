package com.example.neteasenews.ui.freed

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neteasenews.NetEaseApplication
import com.example.neteasenews.databinding.ItemFreedbackViewBinding
import de.hdodenhof.circleimageview.CircleImageView


class HotPostAdapter(private val freedBacksList: ArrayList<FreedBacks>) :
    RecyclerView.Adapter<HotPostAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemFreedbackViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageFreedIcon: CircleImageView = binding.imageFreedbackIcon
        val freedName: TextView = binding.tvFreedbackName
        val freedPraise: TextView = binding.tvPraise
        val freedContent: TextView = binding.tvFreedbackContent
        val freedLLPraise: LinearLayout = binding.llPraise

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding =
            ItemFreedbackViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = freedBacksList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val freedBacks = freedBacksList[position]
        val hotPostList = freedBacks.getFreedBack()

        if (hotPostList.isNotEmpty()) {
            val hotPost = hotPostList[hotPostList.size - 1]
            if (hotPost.timg != null && hotPost.timg != "") {
                Glide.with(NetEaseApplication.context).load(hotPost.timg)
                    .into(holder.imageFreedIcon)
            }
            holder.freedName.text = if (TextUtils.isEmpty(hotPost.n)) "手机网友" else hotPost.n
            holder.freedContent.text = hotPost.b
            holder.freedPraise.text = hotPost.v
        }
    }

}