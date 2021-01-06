package com.begemot.newspapers

import com.begemot.knewscommon.KArticle

import com.begemot.translib.splitLongText
import kotlinx.serialization.Serializable
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import com.begemot.knewscommon.INewsPaper

object RT : INewsPaper {
    override val olang: String
        get() = "ru"
    override val name: String
        get() = "RT Novesti"
    override val desc: String
        get() = "Russian News"
    override val logoName: String
        get() = "rt-logo.png"
    override val handler: String
        get() = "RT"

    override fun getOriginalHeadLines(): List<KArticle> {
        fun transFigure(el: Element): KArticle {
            val title = el.select("h3").text()
            //val title="["+title1.replace('«','-')
            // .replace('»','-')
            // .replace('.',',')
            // .replace('—',' ')
            // .replace('?','?')+"]. "
            val link = el.select("a[href]").first().attr("abs:href")
            return KArticle(title, link)
        }
        fun transSection(el: Element): KArticle {
            val title = el.select("h3").text()
            //val title="["+title1.replace('«','-')
            //.replace('»','-')
            //.replace('.',',')
            // .replace('—',' ')
            // .replace('?','?')+"]. "

            val link = el.select("h3").select("a[href]").attr("abs:href")
            return KArticle(title, link)
        }

        val s = "https://russian.rt.com/inotv"
        val con= Jsoup.connect(s)
        //con.timeout(6000)
        val doc = con.get()
        var art = doc.select("figure")
        val l1 = art.map { it -> transFigure(it) }

        art = doc.select("section.block-white.materials-preview").select("article")
        val l2 = art.map { it -> transSection(it) }
        return l1 + l2

  }

    override fun getOriginalArticle(link: String): List<String> {
        val strbuild=StringBuilder()
        fun transArticleintro(el: Element): String {
            return el.text()
        }

        val doc = Jsoup.connect(link).get()
        val art = doc.select("div.article-intro")
        //val l1 = art.map { it -> transArticleintro(it) }

        art.forEach {
            strbuild.append(transArticleintro(it))
        }

        //println("l1 ${l1.size}  $l1")

        val arts = doc.select("div.article-body")
        arts.forEach {
            strbuild.append(transArticleintro(it))
        }
        //val l2 = arts.map { it -> transArticleintro(it) }
        //println("l2 ${l2.size} $l2")
        //val lt= (l1 + l2).joinToString(separator = " ")
        //return l1+l2
        return splitLongText(strbuild)
    }

}