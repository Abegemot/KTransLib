package com.begemot.newspapers

import com.begemot.knewscommon.INewsPaper
import com.begemot.knewscommon.KArticle
import com.begemot.knewscommon.print
import com.begemot.translib.splitLongText
import com.begemot.translib.splitLongTextChinese
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


private val loggerk = KotlinLogging.logger {}

object PCh : INewsPaper {
    override val olang: String
        get() = "zh"
    override val name: String
        get() = "People daily"
    override val desc: String
        get() = "Chinese News"
    override val logoName: String
        get() = "ydlogo.png"
    override val handler: String
        get() = "PCh"
    override val url: String
        get() = "http://www.people.com.cn/"

    override fun getOriginalHeadLines(): List<KArticle> {
        fun transFigure(el:Element):KArticle{
            val title=el.text()
            val link=el.attr("href")
            return KArticle(title,link)
        }
        val s="http://www.people.com.cn/"
        val doc=Jsoup.connect(s).get()
        val art=doc.select("a[href]")
        //println(art.text())

        return art.map{transFigure(it)}.filter { it.title.length>20 && it.link.isNotEmpty()}
    }

    override fun getOriginalArticle(link: String): String {
        val strbuild=StringBuilder()
        val doc=Jsoup.connect(link).get()
        val l= mutableListOf("")
        l.add(doc.select("h1").text())
        //val l2=doc.select("p").forEach { it->l.add(it.text()) }
        //loggerk.debug { l.print("getOriginal Article PCh.kt") }
        l.add(doc.select("p").text())
        l.forEach{strbuild.append(it);strbuild.append(" ")}
        //chinese dot 。
        //return splitLongTextChinese(strbuild)

        loggerk.debug { strbuild.toString() }
        return strbuild.toString()
        //return l
    }

}