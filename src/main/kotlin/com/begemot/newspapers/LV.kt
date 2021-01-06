package com.begemot.newspapers

import com.begemot.knewscommon.KArticle
import com.begemot.knewscommon.INewsPaper
import com.begemot.translib.splitLongText
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


object LV : INewsPaper {
    override val olang: String
        get() = "es"
    override val name: String
        get() = "La Vanguardia"
    override val desc: String
        get() = "Spanish News"
    override val logoName: String
        get() = "La_Vanguardia.png"
    override val handler: String
        get() = "LV"

    override fun getOriginalHeadLines(): List<KArticle> {
        fun transFigure(el: Element): KArticle {
           // println(el.text())
            val title = el.text()
            val link = "https://www.lavanguardia.com"+el.select("a[href]").first()?.attr("href") ?: ""
           // println(link)
            return KArticle(title, link)
        }


        val s = "https://www.lavanguardia.com"
        val con= Jsoup.connect(s)
        //con.timeout(6000)
        val doc = con.get()
        var art = doc.select("div.article-details")
        val l1 = art.map { it -> transFigure(it) }

        return l1.filter { it.title.isNotEmpty() }

    }

    override fun getOriginalArticle(link: String): List<String> {
        val strbuild=StringBuilder()
        fun transArticleintro(el: Element): String {
            return el.text()
        }

        val doc = Jsoup.connect(link).get()
        val art = doc.select("div.article-intro")


        art.forEach {
            strbuild.append(transArticleintro(it))
        }

        val arts = doc.select("p.paragraph")
        arts.forEach {
            strbuild.append(transArticleintro(it))
        }

        return splitLongText(strbuild)
    }

}