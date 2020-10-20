package com.begemot.translib

import com.begemot.knewscommon.*
import kotlinx.serialization.json.Json
//import kotlinx.serialization.json.JsonConfiguration
import org.jsoup.Connection
import org.jsoup.Jsoup

fun getOriginalHeadLines(namepaper:String):List<KArticle>{
    val iNewsPaper= MBAPE.P[namepaper] ?: throw Exception(" Wrong news paper name!! : $namepaper")
    return iNewsPaper.getOriginalHeadLines()
}


fun getTranslatedHeadLines(namepaper:String, tlang:String):List<OriginalTransLink>{
    println("getTranslatedHeadlines 1")
    val iNewsPaper= MBAPE.P[namepaper] ?: throw Exception(" Wrong news paper name!! : $namepaper")
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

fun translateHeadLines(lA:List<KArticle>, olang: String, tlang: String):List<OriginalTransLink>{
    if(olang.equals(tlang)){
        val lOTL= mutableListOf<OriginalTransLink>()
        lA.forEach {
            lOTL.add(OriginalTransLink(it,""))
            //lOTL.add(OriginalTransLink(it,it.title))
        }
        return lOTL
    }
    val js= lKArticlesToJsonForTranslate(lA,olang,tlang)
    val Z= translateJson(js.value)
    val lt= JsonToListStrings(Z)
    return lA.zip(lt) { a, c->OriginalTransLink(a,c.translatedText)}
}


private fun lKArticlesToJsonForTranslate(lA:List<KArticle>, olang:String, tlang:String): JasonString {
    if(lA.size==0) return JasonString("NOP")
    val x= jsonTrans(lA.map{it.title},olang,tlang,"text")
    println("->x<-$x")
    val rq= kjson.encodeToString(jsonTrans.serializer(),x)
    println("->rq<-$rq")
    return JasonString(rq)
}

private fun translateJson(sjason:String): String {
    val apikey="AIzaSyBP1dsYp-jPF6PfVetJWcguNLiFouZ3mjo"
    val sUrl="https://www.googleapis.com/language/translate/v2?key=$apikey"
    //Timber.d("URL: $sUrl")
    println("->json: $sjason")
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

private fun JsonToListStrings(json:String):List<Translations>{
    val ls= mutableListOf<String>()
    println("zzzzzzzzzzzzzzzzzzzzzzzzz  !!!!!!!!!")
    println(json)
    val topic= kjson.decodeFromString(Json4Kotlin_Base.serializer(),json)
    //val topic = Gson().fromJson(json, Json4Kotlin_Base::class.java)
    return topic.data.translations
}
