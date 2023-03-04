package com.begemot.newspapers

import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.KArticle


object TXS : IBook {

    override val googleDir: String
        get() = "TxehovStories"
    override val olang: String
        get() = "ru"
    //
    override val name: String
        get() = "чеховские рассказы"
    override val desc: String
        get() = "Stories from Txehov"
    override val logoName: String
        get() = "chekhov.jpg"
    override val url: String
        get() = "https://en.wikipedia.org/wiki/Category:Short_stories_by_Anton_Chekhov"

    override fun getOriginalHeadLines(): List<KArticle> {
        //return fromJStr<List<KArticle>>(KNews().getFileContent(sbookname).sresult)
        return listOf(
            KArticle("Дама с собачкой","1"),
            KArticle("Каштанка","2"),
        )
    }

    override val mutable = true

}