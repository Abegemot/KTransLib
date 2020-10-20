package com.begemot.newspapers

import com.begemot.knewscommon.KArticle
import com.begemot.translib.INewsPaper
import com.begemot.translib.splitLongText
import kotlinx.serialization.Serializable
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
            var epi=el.select("span.story-epigraph").text().trim()
            if(epi.length>0) epi+=". "
            val title ="${epi}${el.select("a.story-header-title-link").attr("title")}"
            val link = el.select("a[href]").first().attr("href")
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

        val s = "https://www.lavanguardia.com"
        val con= Jsoup.connect(s)
        //con.timeout(6000)
        val doc = con.get()
        println(doc.text())
        var art = doc.select("header.story-header")
        val l1 = art.map { it -> transFigure(it) }

        //art = doc.select("section.block-white.materials-preview").select("article")
        //val l2 = art.map { it -> transSection(it) }
        //return l1 + l2
        return l1

    }

    override fun getOriginalArticle(link: String, strbuild: StringBuilder): List<String> {

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