package com.example.neteasenews.logic.model

data class Sports(val T1348649079062: List<SportsDetail>)
data class SportsDetail(
    val source: String, val postid: String, val title: String, val mtime: String,
    val digest: String, val imgsrc: String, val docid: String, val url: String,
    val replyCount: Int
)
