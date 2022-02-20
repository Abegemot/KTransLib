package com.begemot.newspapers

import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.KArticle

object DeadSoulsI : IBook {

    override val googleDir: String
        get() = "Gogol/DeadSouls1"
    override val olang: String
        get() = "ru"
    override val name: String
        get() = "Мёртвые души I"
    override val desc: String
        get() = "Russian Book"
    override val logoName: String
        get() = "deadsouls.jpg"
    override val url: String
        get() = "https://en.wikipedia.org/wiki/Dead_Souls"

    override fun getOriginalHeadLines(): List<KArticle> {
        return listOf(
            KArticle("1", "1"),
            KArticle("2", "2"),
            KArticle("3", "3"),
            KArticle("4", "4"),
            KArticle("5", "5"),
            KArticle("6", "6"),
            KArticle("7", "7"),
            KArticle("8", "8"),
            KArticle("9", "9"),
            KArticle("10", "10"),
            KArticle("11", "11"),
        )
    }
}