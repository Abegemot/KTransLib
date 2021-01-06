package com.begemot.newspapers

import com.begemot.knewscommon.INewsPaper
import com.begemot.knewscommon.KArticle
import com.begemot.translib.splitLongText
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object LM:INewsPaper {
    override val olang: String
        get() = "fr"
    override val name: String
        get() = "Le Monde"
    override val desc: String
        get() = "French News"
    override val logoName: String
        get() = "Lemonde.png"
    override val handler: String
        get() = "LM"


    override fun getOriginalHeadLines(): List<KArticle> {
        fun transFigure(el: Element): KArticle {
            val title = el.select("p.article__title").text()
            val link = el.select("a[href]").attr("href")
            return KArticle(title, link)

        }

        val s = "https://www.lemonde.fr/"
        val con = Jsoup.connect(s)
        val doc = con.get()

        var art = doc.select("span.article__title-label")

        //art=doc.select("p.article__title")
        //println("art  $art")

        art = doc.select("a.article--list")
        //println("artK ${l1.size} $l1")


        return art.map { it -> transFigure(it) }
    }

    override fun getOriginalArticle(link: String): List<String> {
        val strbuild=StringBuilder()
        val doc = Jsoup.connect(link).get()
        val art = doc.select("p.article__paragraph.article__paragraph--lf")
        art.forEach{
            strbuild.append(it.text())
        }
        return splitLongText(strbuild)

    }

}