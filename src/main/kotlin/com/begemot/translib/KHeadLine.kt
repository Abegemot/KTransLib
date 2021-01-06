package com.begemot.translib

import com.begemot.knewscommon.*
import kotlinx.coroutines.*


//import kotlinx.serialization.json.JsonConfiguration
import org.jsoup.Connection
import org.jsoup.Jsoup
import kotlin.system.measureTimeMillis

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
    println("translate HeadLines nArticles=${lA.size} ")
    if(lA.isEmpty()) throw Exception("Empty HeadLines!!")
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
    println("---->XXXXXX translatedJson")
    println(Z)
    println("XXXXXX<-----translatedJason")

    val lt= JsonToListStrings(Z)
    val lOTL=lA.zip(lt) { a, c->OriginalTransLink(a,c.translatedText)}
    println("Size lOTL = ${lOTL.size}")
    lOTL.print("After Translate")
    if(tlang.equals("zh") || tlang.equals("zh-TW"))  return addPinyinOTL(lOTL,false)
    if(olang.equals("zh"))  return addPinyinOTL(lOTL,true)
    else return lOTL
   // return lA.zip(lt) { a, c->OriginalTransLink(a,c.translatedText)}
}









private fun lKArticlesToJsonForTranslate(lA:List<KArticle>, olang:String, tlang:String): JasonString {
    println("lKArticlesToJsonForTranslate  ${lA.size}")
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
    println("translate json")
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





