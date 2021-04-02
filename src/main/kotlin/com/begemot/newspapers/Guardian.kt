package com.begemot.newspapers

import com.begemot.knewscommon.KArticle

import com.begemot.knewscommon.INewsPaper
import com.begemot.translib.addStringWithEndPoint
import com.begemot.translib.splitLongText
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document



object GU : INewsPaper {
    override val olang: String
        get() = "en"
    override val name: String
        get() = "The Guardian"
    override val desc: String
        get() = "British News"
    override val logoName: String
        get() = "The_Guardian.png"
    override val handler: String
        get() = "GU"
    override val url: String
        get() = "https://www.theguardian.com/international"

    override fun getOriginalHeadLines(): List<KArticle> {
        println("GU getOriginal headlines")
        val doc = Jsoup.connect("https://www.theguardian.com/international").get()
        val u = doc.select("a.u-faux-block-link__overlay.js-headline-text")
        return u.map { it -> KArticle(it.text(), it.attr("href")) }//.subList(7,40)
    }

    override fun getOriginalArticle(link: String): List<String> {
        val strbuild=StringBuilder()
        val con = Jsoup.connect(link)
        val resp: Connection.Response = con.execute()
        var doc: Document?
        if (resp.statusCode() == 200) {
            doc = con.get();
        } else {

            println("Guardian error geting original article resp status code = ${resp.statusCode()}")
            println(resp.statusMessage())
            println("end Guardian")
            return emptyList()
        }


        //val doc= Jsoup.connect(slink).get()
        println(doc.text())
        //println("html->")
        //println(doc.html())

        val q = doc.select("h1.content__headline ")

        val title = q.text()
        println("title ${q.size} ${q.text()} end title")
        addStringWithEndPoint(title, strbuild)

        println("title sb ${strbuild.toString()}")

       // var query = "div.content__article-body from-content-api js-article__body"
        val query = "[data-test-id='article-review-body']"
        val pq = doc.select(query)
        println("pq size ${pq.size}")

        var art = pq.select("p")
        println("paragraf number ${art.size}")

        if (art.size == 0) {
            println(" No paragraphs found!! ")
            art = doc.select("p")
            println("paragraf number ${art.size}")
            //   println(doc.html())
            println(" No paragraphs found!! ")
        } else {
            println("Paragraphs ok")
            //   println(doc.html())
            println("Paragraphs ok")
        }

        //val l1=art.map{it.text()}
        art.forEach {
            addStringWithEndPoint(it.text(), strbuild)
        }
        println("textsb $strbuild")
        return splitLongText(strbuild)

    }


}
