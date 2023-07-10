package com.example.neteasenews.ui.freed


class FreedBacks() {
    private var freedBacks: MutableList<HotPost>

    init {
        freedBacks = ArrayList<HotPost>()
    }


    fun getFreedBack(): List<HotPost> {
        return freedBacks
    }

    fun setFreedBack(mFreedback: MutableList<HotPost>) {
        freedBacks = mFreedback
    }

    fun addFreedback(hotPost: HotPost) {
        freedBacks.add(hotPost)
    }
}

data class HotPost(
    val a: String, val b: String, val bi: String, val d: String, val f: String,
    val fi: String, val ip: String, val l: String, val n: String, val p: String,
    val pi: String, val rp: String, val source: String, val t: String, val timg: String,
    val u: String, val v: String, val vDuration: Int
)
