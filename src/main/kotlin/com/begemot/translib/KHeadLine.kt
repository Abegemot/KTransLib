package com.begemot.translib

import com.begemot.knewscommon.*
import kotlinx.coroutines.*
import mu.KotlinLogging


//import kotlinx.serialization.json.JsonConfiguration
import org.jsoup.Connection
import org.jsoup.Jsoup
import kotlin.system.measureTimeMillis
private val logger = KotlinLogging.logger {}


fun getOriginalHeadLines(namepaper:String):List<KArticle>{
    val iNewsPaper= MBAPE.P[namepaper] ?: throw Exception(" Wrong news paper name!! : $namepaper")
    return iNewsPaper.getOriginalHeadLines()
}


fun getTranslatedHeadLines(namepaper:String, tlang:String):List<OriginalTransLink>{
    logger.debug { "getTranslatedHeadlines 1" }
    val iNewsPaper= MBAPE.P[namepaper] ?: throw Exception(" Wrong news paper name!! : $namepaper")

    val lHl=iNewsPaper.getOriginalHeadLines()
    logger.debug { "getTranslatedHeadlines 3 size lhl ${lHl.size}" }

    //lHl.print("before translate")
    return translateHeadLines(lHl,iNewsPaper.olang,tlang)

/*    val js= lKArticlesToJsonForTranslate(lHl,iNewsPaper.olang,tlang)
  //  println("js ${js.value}")
    val Z= translateJson(js.value)
    //println("translated json $Z")
    val lt= JsonToListStrings(Z)
    return lHl.zip(lt) { a, c->OriginalTransLink(a,c.translatedText)}
    */

}

fun translateHeadLines(lA:List<KArticle>, olang: String, tlang: String):List<OriginalTransLink>{
    logger.debug{"translate HeadLines nArticles=${lA.size} "}
    if(lA.isEmpty()) throw Exception("Empty HeadLines!!")
    if(olang.equals(tlang)){
        val lOTL= mutableListOf<OriginalTransLink>()
        lA.forEach {
            lOTL.add(OriginalTransLink(it,""))
            //lOTL.add(OriginalTransLink(it,it.title))
        }
        if(olang.equals("zh")) return addPinyinOTL(lOTL,true)
        return lOTL
    }
    val js= lKArticlesToJsonForTranslate(lA,olang,tlang)
    val Z= translateJson(js.value)
    logger.debug{"translated JSON  $Z\nend"}

    val lt= JsonToListStrings(Z)
    val lOTL=lA.zip(lt) { a, c->OriginalTransLink(a,c.translatedText)}

    logger.debug{ "Size lOTL = ${lOTL.size}"}
    logger.debug{ "after translate :${lOTL.print("After Translate")}" }
    if(tlang.equals("zh") || tlang.equals("zh-TW"))  return addPinyinOTL(lOTL,false)
    if(olang.equals("zh"))  return addPinyinOTL(lOTL,true)
    else return lOTL
   // return lA.zip(lt) { a, c->OriginalTransLink(a,c.translatedText)}
}









private fun lKArticlesToJsonForTranslate(lA:List<KArticle>, olang:String, tlang:String): JasonString {
    logger.debug { "lKArticlesToJsonForTranslate  ${lA.size}"}
    if(lA.size==0) return JasonString("NOP")
    val x= jsonTrans(lA.map{it.title},olang,tlang,"text")
   // println("->x<-$x")
   // val rq= kjson.encodeToString(jsonTrans.serializer(),x)
    //println("->rq<-$rq")
    return JasonString(x.toStr())
}

private fun translateJson(sjason:String): String {
    val apikey="AIzaSyBP1dsYp-jPF6PfVetJWcguNLiFouZ3mjo"
    val sUrl="https://www.googleapis.com/language/translate/v2?key=$apikey"
    //Timber.d("URL: $sUrl")
    //println("->json: $sjason")
    logger.debug { "translate json" }
    val cr= Jsoup.connect(sUrl)
        .header("Content-Type","application/json; charset=utf-8")
        .header("Accept","application/json")
        //.followRedirects(true)
        .ignoreContentType(true)
        .ignoreHttpErrors(true)
        .method(Connection.Method.POST)
        .requestBody(sjason)
        .execute()
    return cr.body()
}





