package com.example.neteasenews.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neteasenews.databinding.ActivityDetailimageBinding
import com.github.chrisbanes.photoview.PhotoView

class DetailImageActivity : AppCompatActivity() {
    companion object {
        const val IMAGEURL: String = "imageUrl"
    }

    private var _binding: ActivityDetailimageBinding? = null

    private val binding: ActivityDetailimageBinding
        get() = _binding!!

    private lateinit var images: ArrayList<DetailImage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailimageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        images = intent.getSerializableExtra(IMAGEURL) as ArrayList<DetailImage>
        if (images != null) {
            var mView = ArrayList<PhotoView>()
            for (i in 0 until images.size) {
                val photoView = PhotoView(this)
                mView.add(photoView)
            }
            val adapter = DetailImageAdapter(mView, images)
            binding.imageViewpager.adapter = adapter
        }

    }

}