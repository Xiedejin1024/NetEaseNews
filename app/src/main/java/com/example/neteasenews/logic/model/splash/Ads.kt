package com.example.neteasenews.logic.model.splash

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Ads(val ads: List<AdsDetail>, val next_req: Int, val result: Int, val rolls: Int) :
    Serializable

data class AdsDetail(val actionList: List<Action>, val materialList: List<MaterialList>) :
    Serializable

data class Action(val url: String) :
    Serializable

data class MaterialList(val urls: List<String>) :
    Serializable
