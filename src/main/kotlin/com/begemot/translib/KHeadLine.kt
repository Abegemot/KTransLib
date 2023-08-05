package com.begemot.translib

import com.begemot.kcache.KCache
import com.begemot.knewscommon.*
import kotlinx.coroutines.*
import mu.KotlinLogging


//import kotlinx.serialization.json.JsonConfiguration
import org.jsoup.Connection
import org.jsoup.Jsoup
import kotlin.system.measureTimeMillis
private val logger = KotlinLogging.logger {}


fun getOlang(namepaper: String):String{
    val iNewsPaper= MBAPE.P[namepaper] ?: throw Exception(" Wrong news paper name!! : $namepaper")
    return iNewsPaper.olang
}

fun getOriginalHeadLines(namepaper:String):List<KArticle>{
    val iNewsPaper= MBAPE.P[namepaper] ?: throw Exception(" Wrong news paper name!! : $namepaper")
    if(iNewsPaper.handler.equals("TXS")){
    //    val sbookname=iNewsPaper.getGoogleFullPath() //"Books/${iNewsPaper.googleDir}/${iNewsPaper.handler}"
        val sbookname=iNewsPaper.getGoogleHeadLinesDir()
        return fromJStr<List<KArticle>>( KCache().findInCache(sbookname).sresult)
    }
    return iNewsPaper.getOriginalHeadLines()
}


suspend fun getTranslatedHeadLines(namepaper:String, tlang:String):List<OriginalTransLink>{
    logger.debug { "getTranslatedHeadlines of $namepaper translated to $tlang" }
    val iNewsPaper= MBAPE.P[namepaper] ?: throw Exception(" Wrong news paper name!! : $namepaper")
    val lHl=iNewsPaper.getOriginalHeadLines()
    logger.debug { "TranslatedHeadlines size List<KArticle> hl ${lHl.size}" }
    return translateHeadLines(lHl,iNewsPaper.olang,tlang)
}

suspend fun translateHeadLines(lA:List<KArticle>, olang: String, tlang: String):List<OriginalTransLink>{
    logger.debug{"translate HeadLines nArticles=${lA.size} "}
    if(lA.isEmpty()) return emptyList() //throw Exception("Empty HeadLines!!")
    if(olang.equals(tlang)){
        val lOTL= mutableListOf<OriginalTransLink>()
        lA.forEach {
            lOTL.add(OriginalTransLink(it,""))
        }
        if(olang.equals("zh")) return addPinyinOTL(lOTL,true)
        return lOTL
    }
    val lt= translateJson2(jsonTrans(lA.map{it.title},olang,tlang,"text"))
    val lOTL=lA.zip(lt) { a, c->OriginalTransLink(a,c.translatedText)}
    var lOTL2= lOTL//listOf<OriginalTransLink>()
    if(tlang.equals("zh") )  lOTL2 = addPinyinOTL(lOTL,false)
    if(olang.equals("zh"))   lOTL2 = addPinyinOTL(lOTL,true)

    logger.debug { lOTL2.print("Result") }
    return lOTL2
}


/*private fun lKArticlesToJsonForTranslate(lA:List<KArticle>, olang:String, tlang:String): jsonTrans {
    logger.debug { "lKArticlesToJsonForTranslate  ${lA.size}"}
    return jsonTrans(lA.map{it.title},olang,tlang,"text")
}*/

/*private fun translateJson(sjason:String): String {
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
}*/





