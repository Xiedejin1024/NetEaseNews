package com.example.neteasenews.logic.model

data class Headline(val T1348647909107: List<HotsDetail>)
data class HotsDetail(
    val source: String, val postid: String, val title: String, val mtime: String,
    val digest: String, val imgsrc: String, val docid: String, val url: String,
    val ads: List<Ads>, val replyCount: Int
)

data class Ads(
    val subtitle: String, val skipType: String, val skipID: String,
    val tag: String, val title: String, val imgsrc: String, val url: String
)
