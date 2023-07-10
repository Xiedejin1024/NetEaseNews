package com.example.neteasenews

import android.app.Application
import android.content.Context


class NetEaseApplication : Application() {

    companion object {
        @SuppressWarnings("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //采用ImageLoad加载图片时,在高版本时出现有些异常:ImageLoader: No field mMaxHeight in class Landroid/widget/ImageView;
        //解决方式:https://www.likecs.com/ask-1506097.html(但是本项目不能成功解决)


        //val configuration = ImageLoaderConfiguration.createDefault(this)
        /* ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .discCache(new UnlimitedDiscCache(this.getCacheDir()))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .build();*/
        //ImageLoader.getInstance().init(configuration)
    }

}