package com.begemot.newspapers

import com.begemot.knewscommon.INewsPaper
import com.begemot.knewscommon.KArticle
import com.begemot.knewscommon.print
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

private val logger = KotlinLogging.logger {}

object KP :INewsPaper {
    override val olang: String
        get() = "ru"
    override val name: String
        get() = "комсомольская правда"
    override val desc: String
        get() = "Russian News"
    override val logoName: String
        get() = "komcomol.png"
    override val handler: String
        get() = "KP"
    override val url: String
        get() = "https://www.kp.ru/"


    override fun getOriginalHeadLines(): List<KArticle> {

        fun transTwo(el:Element):KArticle{
            return KArticle(el.text(),el.attr("abs:href"))
        }
       val doc=Jsoup.connect(url).get()
       val s = "a[href]"
       val art=doc.select(s)
      // logger.debug { art.text() }
       val l1=art.map{it->transTwo(it)}.filter {
            it.link.substringBeforeLast("/").last().isDigit()
                    &&
            it.title.length>15
       }
        //logger.debug{l1.print()}
        logger.debug { "NArticles=${l1.size}" }
        return l1
    }

    override fun getOriginalArticle(link: String): String {
        val strbuild=StringBuilder()
//        logger.debug { "link $link" }
        var s="p.styled__Paragraph-sc-1wayp1z-16.fNsfGH"
        //var s="p.styled__Paragraph-sc-17amg0v-0.ivlOHa"
        s="p"
        val doc=Jsoup.connect(link).get()
        val art=doc.select(s)
        art.forEach {
           //logger.debug { "JK->${it.text()}" }
           strbuild.append(it.text())
           strbuild.append(" ")
        }
  //      logger.debug { art.size }
        return strbuild.toString()
    }

}