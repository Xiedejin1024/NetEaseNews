package com.example.neteasenews.ui.detail

import java.io.Serializable

data class DocDetail(
    val body: String,
    val title: String,
    val ptime: String,
    val threadVote: Int,
    val img: List<DetailImage>,
    val source: String,
    val replyCount: Int,
    val shareLink: String
) : Serializable

data class DetailImage(val ref: String, val src: String, val alt: String, val pixel: String) :
    Serializable