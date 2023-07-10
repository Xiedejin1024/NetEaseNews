package com.example.neteasenews.logic.network

object UrlContance {

    const val SPLASH_URL =
        "http://g1.163.com/madr?app=7A16FBB6&platform=android&category=STARTUP&location=1&timestamp=1462779408364&uid=OaBKRDb%2B9FBz%2FXnwAuMBWF38KIbARZdnRLDJ6Kkt9ZMAI3VEJ0RIR9SBSPvaUWjrFtfw1N%2BgxquT0B2pjMN5zsxz13RwOIZQqXxgjCY8cfS8XlZuu2bJj%2FoHqOuDmccGyNEtV%2FX%2FnBofofdcXyudJDmBnAUeMBtnIzHPha2wl%2FQnUPI4%2FNuAdXkYqX028puyLDhnigFtrX1oiC2F7UUuWhDLo0%2BE0gUyeyslVNqLqJCLQ0VeayQa%2BgbsGetk8JHQ"
    const val SPLASH_WORKREQUEST = "input_data"
    const val CHILDFILE = "netease"


    const val HOME_URL = "http://c.m.163.com/nc/article/headline/T1348647909107/%S-%E.html?"
    fun getRefreshURL(net_url: String, start: Int, end: Int): String? {
        var url = net_url.replace("%S", start.toString())
        url = url.replace("%E", end.toString())
        return url
    }


    const val DOCIDURL = "http://c.m.163.com/nc/article/%D/full.html"

    fun getDetailURL(docid: String): String {
        return DOCIDURL.replace("%D", docid)
    }

    const val FREEDBACKURL =
        "http://comment.api.163.com/api/json/post/list/new/hot/news3_bbs/%D/0/30/30/2/2"

    fun getFreedbackURL(docid: String): String {
        return FREEDBACKURL.replace("%D", docid)
    }

    //体育Url
    const val SPORTS_URL = "http://c.m.163.com/nc/article/headline/T1348649079062/%S-%E.html?"

    //广州
    const val GuangzhouId = "http://c.m.163.com/nc/article/local/5bm/5bee/%S-%E.html?"

    const val Native_URL = "http://c.m.163.com/nc/article/headline/T1348648650048/%S-%E.html?"

   /* //本地
    const val NativeId = "http://c.m.163.com/nc/article/local/%D/%S-%E.html?"

    fun getNativeURL(address: String): String {
        return NativeId.replace("%D", address)
    }*/


    // 足球
    const val ZuQiuId = "T1399700447917"

    // 娱乐
    const val YuLeId = "T1348648517839"

    // 财经
    const val CaiJingId = "T1348648756099"

    // 科技
    const val KeJiId = "T1348649580692"

    // 电影
    const val DianYingId = "T1348648650048"

    // 汽车
    const val QiCheId = "T1348654060988"

    // 笑话
    const val XiaoHuaId = "T1350383429665"

    // 游戏
    const val YouXiId = "T1348654151579"

    // 时尚
    const val ShiShangId = "T1348650593803"

    // 情感
    const val QingGanId = "T1348650839000"

    // 精选
    const val JingXuanId = "T1370583240249"

    // 电台
    const val DianTaiId = "T1379038288239"

    // nba
    const val NBAId = "T1348649145984"

    // 数码
    const val ShuMaId = "T1348649776727"

    // 移动
    const val YiDongId = "T1351233117091"

    // 彩票
    const val CaiPiaoId = "T1356600029035"

    // 教育
    const val JiaoYuId = "T1348654225495"

    // 论坛
    const val LunTanId = "T1349837670307"

    // 旅游
    const val LvYouId = "T1348654204705"

    // 手机
    const val ShouJiId = "T1348649654285"

    // 博客
    const val BoKeId = "T1349837698345"

    // 社会
    const val SheHuiId = "T1348648037603"

    // 家居
    const val JiaJuId = "T1348654105308"

    // 暴雪游戏
    const val BaoXueYouXiId = "T1397016069906"

    // 亲子
    const val QinZiId = "T1397116135282"

    // CBA
    const val CBAId = "T1348649475931"

    // 消息
    const val XiaoXiId = "T1371543208049"

}