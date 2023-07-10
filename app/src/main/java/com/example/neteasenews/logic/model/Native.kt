package com.example.neteasenews.logic.model

data class Native(val T1348648650048: List<NativeDetail>)
data class NativeDetail(
    val source: String, val postid: String, val title: String, val lmodify: String,
    val digest: String, val imgsrc: String, val docid: String, val url: String,
    val replyCount: Int
)
