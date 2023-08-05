package com.begemot.newspapers

import com.begemot.knewscommon.INewsPaper
import com.begemot.knewscommon.KArticle
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

private val logger = KotlinLogging.logger {}
object VW :INewsPaper {
    override val olang: String
        get() = "ca"
    override val name: String
        get() = "VilaWeb"
    override val desc: String
        get() = "Catalan News"
    override val logoName: String
        get() = "vilaweb.jpg"
    override val url: String
        get() = "https://www.vilaweb.cat"

    override fun getOriginalHeadLines(): List<KArticle> {
        fun transTwo(el: Element):KArticle{
            return KArticle(el.text(),el.attr("abs:href"))
        }
        val doc=Jsoup.connect(url).get()
        var art=doc.select("a[href]")
        val l1=art.map{it->transTwo(it)}.filter {
            it.link.contains("noticies") //   .substringBeforeLast("/").last().isDigit()
                    &&
                    it.title.length>15
        }
        //logger.debug{l1.print()}
        logger.debug { "NArticles=${l1.size}" }
        return l1.distinctBy { it->it.link }
    }

    override fun getOriginalArticle(link: String): String {
        logger.debug { link }
        val strbuild=StringBuilder()
        //var s="div.content-noticia-body"
        val doc=Jsoup.connect(link).get()
        val art=doc.select("p:not([id])")
        art.forEach {
            //logger.debug { it.parent()?.html() }
            //logger.debug { it.html() }
            //logger.debug { it.attributes() }
            //if(it.select("span").isEmpty()) {
            val prev=it.parent()
            val cn=prev?.className()

            if(cn?.equals("article-content-publi px-3 content-noticia-body relative") == true) {
                logger.debug { it.text() }
                strbuild.append(it.text())
                strbuild.append(" ")
            }


            //}
        }
        return strbuild.toString()
    }


}