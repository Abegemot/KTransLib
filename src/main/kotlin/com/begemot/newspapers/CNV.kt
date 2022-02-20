package com.begemot.newspapers

import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.KArticle

object CNV : IBook{
    override val googleDir: String
        get() = "CatalaNV"
    override val olang: String
        get() = "ca"
    override val name: String
        get() = "Català per a nouvinguts"
    override val desc: String
        get() = "Easy Catalan language lessons"
    override val logoName: String
        get() = "PCatalans.jpg"
    override val url: String
        get() = "https://en.wikipedia.org/wiki/Catalan_language"
    override val mutable = true
    override fun getOriginalHeadLines(): List<KArticle> {
       return  listOf<KArticle>(
            KArticle("Els numeros","1"),
            KArticle("El temps cronologic","2"),
            KArticle("Les posicions en l'espai","3")
       )

    }
    override fun getOriginalArticle(link: String): String {
        return "sopa"
    }
}