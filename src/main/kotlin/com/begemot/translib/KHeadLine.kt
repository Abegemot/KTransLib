package com.begemot.translib

import com.begemot.knewscommon.*
import com.begemot.translib.MBAPE
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.time.LocalDateTime

fun getOriginalHeadLines(name:String):List<KArticle>{
    val iNewsPaper= MBAPE.P[name] ?: return emptyList()
    return iNewsPaper.getOriginalHeadLines()
}


fun getTranslatedHeadLines(name:String,tlang:String):List<OriginalTransLink>{
    println("getTranslatedHeadlines 1")
    val iNewsPaper= MBAPE.P[name] ?: return emptyList()
    println("getTranslatedHeadlines 2")
    val lHl=iNewsPaper.getOriginalHeadLines()
    println("getTranslatedHeadlines 3 size lhl ${lHl.size}")
    return translateHeadLines(lHl,iNewsPaper.olang,tlang)

/*    val js= lKArticlesToJsonForTranslate(lHl,iNewsPaper.olang,tlang)
  //  println("js ${js.value}")
    val Z= translateJson(js.value)
    //println("translated json $Z")
    val lt= JsonToListStrings(Z)
    return lHl.zip(lt) { a, c->OriginalTransLink(a,c.translatedText)}
    */

}

fun translateHeadLines(lA:List<KArticle>,name: String,tlang: String):List<OriginalTransLink>{
    val dList= mutableListOf<OriginalTransLink>(OriginalTransLink(KArticle("$name$tlang  ${LocalDateTime.now()}"),"fake"))
    return dList
    val iNewsPaper= MBAPE.P[name] ?: return emptyList()
    val js= lKArticlesToJsonForTranslate(lA,iNewsPaper.olang,tlang)
    //  println("js ${js.value}")
    val Z= translateJson(js.value)
    //println("translated json $Z")
    val lt= JsonToListStrings(Z)
    return lA.zip(lt) { a, c->OriginalTransLink(a,c.translatedText)}
}


private fun lKArticlesToJsonForTranslate(lA:List<KArticle>, olang:String, tlang:String): JasonString {
    if(lA.size==0) return JasonString("NOP")
    val x= jsonTrans(lA.map{it.title},olang,tlang)
    val rq= Json(JsonConfiguration.Stable).stringify(jsonTrans.serializer(),x)
    return JasonString(rq)
}

private fun translateJson(sjason:String): String {
    val apikey="AIzaSyBP1dsYp-jPF6PfVetJWcguNLiFouZ3mjo"
    val sUrl="https://www.googleapis.com/language/translate/v2?key=$apikey"
    //Timber.d("URL: $sUrl")
    //Timber.d("json: $sjason")
    val cr= Jsoup.connect(sUrl)
        .header("Content-Type","application/json")
        .header("Accept","application/json")
        //.followRedirects(true)
        .ignoreContentType(true)
        .ignoreHttpErrors(true)
        .method(Connection.Method.POST)
        .requestBody(sjason)
        .execute()
    return cr.body()
}

private fun JsonToListStrings(json:String):List<Translations>{
    val ls= mutableListOf<String>()
    val topic=Json(JsonConfiguration.Stable).parse(Json4Kotlin_Base.serializer(),json)
    //val topic = Gson().fromJson(json, Json4Kotlin_Base::class.java)
    return topic.data.translations
}
