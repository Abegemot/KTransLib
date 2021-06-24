package com.begemot.newspapers

import com.begemot.knewscommon.Book
import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.INewsPaper
import com.begemot.knewscommon.KArticle


object LPeste: IBook {

    override val directory: String
        get() = "Camus"
    override val olang: String
        get() = "fr"
    override val name: String
        get() = "La Peste"
    override val desc: String
        get() = "French Book"
    override val logoName: String
        get() = "lapeste.png"
    override val handler: String
        get() = "LPE"
    override val url: String
        get() = "https://en.wikipedia.org/wiki/The_Plague"

    override fun getOriginalHeadLines(): List<KArticle> {
        val lChapters = mutableListOf<KArticle>()
        lChapters.add(KArticle("I","1"))
        lChapters.add(KArticle("II","2"))
        lChapters.add(KArticle("III","3"))
        lChapters.add(KArticle("IV","4"))
        lChapters.add(KArticle("V","5"))
        return lChapters
    }
    override fun getOriginalArticle(link: String): String {
        return ""
    }
}