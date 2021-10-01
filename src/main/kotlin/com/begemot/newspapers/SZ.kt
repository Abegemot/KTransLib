package com.begemot.newspapers

import com.begemot.knewscommon.*
import kotlinx.serialization.Serializable
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.lang.StringBuilder


private val loggerk = KotlinLogging.logger {}

//@Serializable
object SZ : INewsPaper {
    override val kind: KindOfNews
        get() = KindOfNews.NEWS
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

    override fun getOriginalArticle(link: String): String {
        fun transArticleintro(el: Element): String {
            loggerk.debug { "hola" }
            return el.text()
        }
        loggerk.debug { "JOZU!!  SZ getOriginalArticle" }
        val doc = Jsoup.connect(link).get()
        var art = doc.select("p[class=\" css-13wylk3\"]")
        val strb=StringBuilder()
        art.forEach {
           // loggerk.debug { it.text() }
            strb.append(it.text())
            strb.append(" ")
        }

        //return splitLongText(strb)
        return strb.toString()

        //val l1 = art.map { it -> transArticleintro(it) }
        //return l1   //!!splitLongText missed
    }

}