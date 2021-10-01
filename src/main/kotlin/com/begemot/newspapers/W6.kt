package com.begemot.newspapers

import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.KArticle

object W6: IBook {

    override val googleDir: String
        get() = "Chehov"
    override val olang: String
        get() = "ru"
    override val name: String
        get() = "Палата №6"
    override val desc: String
        get() = "Russian Book"
    override val logoName: String
        get() = "palata66.jpg"
   // override val handler: String
   //     get() = this::class.java.simpleName//"W6"
    override val url: String
        get() = "https://en.wikipedia.org/wiki/Ward_No._6"

    override fun getOriginalHeadLines(): List<KArticle> {
        val lChapters = mutableListOf<KArticle>()
        lChapters.add(KArticle("1","1"))
        lChapters.add(KArticle("2","2"))
        lChapters.add(KArticle("3","3"))
        lChapters.add(KArticle("4","4"))
        lChapters.add(KArticle("5","5"))
        lChapters.add(KArticle("6","6"))
        lChapters.add(KArticle("7","7"))
        lChapters.add(KArticle("8","8"))
        lChapters.add(KArticle("9","9"))
        lChapters.add(KArticle("10","10"))
        lChapters.add(KArticle("11","11"))
        lChapters.add(KArticle("12","12"))
        lChapters.add(KArticle("13","13"))
        lChapters.add(KArticle("14","14"))
        lChapters.add(KArticle("15","15"))
        lChapters.add(KArticle("16","16"))
        lChapters.add(KArticle("17","17"))
        lChapters.add(KArticle("18","18"))
        lChapters.add(KArticle("19","19"))
        return lChapters
    }
    override fun getOriginalArticle(link: String): String {
        return ""
    }
}