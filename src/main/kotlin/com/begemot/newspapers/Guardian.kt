  package com.begemot.newspapers

import com.begemot.knewscommon.KArticle
import com.begemot.translib.INewsPaper
import com.begemot.translib.addStringWithEndPoint
import com.begemot.translib.splitLongText
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object GU: INewsPaper {
    override val olang: String
        get() = "en"
    override val nameFile: String
        get() = "guardian"
    override val logoName: String
        get() = "guardianlogo"

    override  fun getOriginalHeadLines(): List<KArticle> {
        println("GU getOriginal headlines")
        val doc= Jsoup.connect("https://www.theguardian.com/international").get()
        val u=doc.select("a.u-faux-block-link__overlay.js-headline-text")
        return u.map{ it-> KArticle(it.text(),it.attr("href")) }//.subList(7,40)


    }

    override fun getOriginalArticle(slink: String, textsb: StringBuilder):List<String> {
        val con=Jsoup.connect(slink)
        val resp:Connection.Response=con.execute()
        var doc:Document?=null
        if (resp.statusCode() == 200) {
            doc = con.get();
        } else{

            println("Guardian error geting original article resp status code = ${resp.statusCode()}")
            println(resp.statusMessage())
            println("end Guardian")
            return emptyList()
        }


        //val doc= Jsoup.connect(slink).get()
        println(doc.text())
        //println("html->")
        //println(doc.html())

        val q=doc.select("h1.content__headline ")

        val title=q.text()
        println("title ${q.size} ${q.text()} end title")
        addStringWithEndPoint(title, textsb)

        println("title sb ${textsb.toString()}")

        var query="div.content__article-body from-content-api js-article__body"
        query="[data-test-id='article-review-body']"
        val pq=doc.select(query)
        println("pq size ${pq.size}")

        val art=pq.select("p")
        println("paragraf number ${art.size}")

        if(art.size==0){
            println(" No paragraphs found!! ")
         //   println(doc.html())
            println(" No paragraphs found!! ")
        }else{
            println("Paragraphs ok")
         //   println(doc.html())
            println("Paragraphs ok")
        }

        //val l1=art.map{it.text()}
        art.forEach{
            addStringWithEndPoint(it.text(), textsb)
        }
        println("textsb $textsb")
        return splitLongText(textsb)

    }


}
