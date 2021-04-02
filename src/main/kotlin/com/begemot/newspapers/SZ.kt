package com.begemot.newspapers

import com.begemot.knewscommon.KArticle
import com.begemot.knewscommon.INewsPaper
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object SZ :INewsPaper{
    override val olang: String
        get() = "de"
    override val name: String
        get() = "Süddeutsche Zeitung"
    override val desc: String
        get() ="German Newspaper"
    override val logoName: String
        get() = "sz-plus-logo.png"
        //get() = "La_Vanguardia.png"
    override val handler: String
        get() = "SZ"
    override val url: String
        get() = "https://www.sueddeutsche.de"

    override fun getOriginalHeadLines(): List<KArticle> {
        fun transFigure(el: Element): KArticle {
            val link =  el.attr("href")
            if(el.text().isEmpty()) return KArticle()
            return KArticle(el.select("h3.sz-teaser__title").text(), link)
        }
        val s = "https://www.sueddeutsche.de"
        val con= Jsoup.connect(s)
        con.timeout(6000)
        val doc = con.get()
        var art = doc.select("a.sz-teaser[href]")
        val l1 = art.map { it -> transFigure(it) }.filter { !it.title.isEmpty() }
        return l1
    }

    override fun getOriginalArticle(link: String): List<String> {

        fun transArticleintro(el: Element): String {
            return el.text()
        }
        val doc = Jsoup.connect(link).get()
        var art = doc.select("p.css-0")
        val l1 = art.map { it -> transArticleintro(it) }
        return l1   //!!splitLongText missed
    }

}