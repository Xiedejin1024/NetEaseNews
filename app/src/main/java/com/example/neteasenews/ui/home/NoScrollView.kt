package com.example.neteasenews.ui.home

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

/*
* GridView 嵌套ScrollView时,会执行获取子控件的高度,影响整体的高度,所以需要重写onMeasure自定义获取高度
*         /**
         * Measure specification mode: The parent has not imposed any constraint
         * on the child. It can be whatever size it wants.
         */
        public static final int UNSPECIFIED = 0 << MODE_SHIFT;

        /**
         * Measure specification mode: The parent has determined an exact size
         * for the child. The child is going to be given those bounds regardless
         * of how big it wants to be.
         */
        public static final int EXACTLY     = 1 << MODE_SHIFT;

        /**
         * Measure specification mode: The child can be as large as it wants up
         * to the specified size.
         */
        public static final int AT_MOST     = 2 << MODE_SHIFT;
*
* */

class NoScrollView(context: Context, attributeSet: AttributeSet) : GridView(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measureSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)

        super.onMeasure(widthMeasureSpec, measureSpec)//传入自定义的宽高
    }
}