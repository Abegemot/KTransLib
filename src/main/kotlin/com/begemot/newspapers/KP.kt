package com.begemot.newspapers

import com.begemot.knewscommon.INewsPaper
import com.begemot.knewscommon.KArticle
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
        fun transOne(el:Element):KArticle{
            val parent=el.parent()
            val title = "${parent.child(0).text()}. ${parent.child(1).text()}"
            //logger.debug{"title: $title"}

            val lll=parent.select("a[href]")[1].attr("href")

            val link = if(lll.substring(0,4).equals("http")){
                lll
            }else{
                "https://www.kp.ru"+lll
            }
            return KArticle(title,link)
        }
       // "styled__DigestItemTitleText-sc-1tputnk-15 eomOUr"
        //div.styled__DigestItemWrapper-sc-1tputnk-16.mVMpP
       val doc=Jsoup.connect(url).get()
       var s="div.styled__TagDesktop-sc-1tputnk-13.clFrTB"

       val art=doc.select(s)
       val l1=art.map{it->transOne(it)}

//       logger.debug{l1.print()}
//       logger.debug { "N=${art.size}" }

      // val art2=doc.select("div.styled__DigestItemWrapper-sc-1tputnk-16.mVMpP")
      //  println("art2 N=${art2.size  }")

        return l1
    }

    override fun getOriginalArticle(link: String): String {
        val strbuild=StringBuilder()
//        logger.debug { "link $link" }
        var s="p.styled__Paragraph-sc-1wayp1z-16.fNsfGH"

        //var s="p.styled__Paragraph-sc-17amg0v-0.ivlOHa"
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