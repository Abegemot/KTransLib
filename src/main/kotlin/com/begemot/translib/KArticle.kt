package com.begemot.translib

import com.begemot.knewscommon.*
import com.begemot.translib.MBAPE.P
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonConfiguration
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.net.URLEncoder
import java.time.LocalDateTime
//import org.json.JSONArray

fun getOriginalArticle(name:String,link:String):StringBuilder{
    val sb=StringBuilder()
    val iNewsPaper= MBAPE.P[name] ?: return sb
    return iNewsPaper.getOriginalArticle(link,sb)
}


fun getTranslatedArticle(name:String,tlang:String,link:String):List<OriginalTrans>{
    val lOriginalTrans= mutableListOf<OriginalTrans>()

    val iNewsPaper= P[name] ?: return emptyList()

    val olang=iNewsPaper.olang
    val str=StringBuilder()
    val tOsb=iNewsPaper.getOriginalArticle(link,str)

    //val tOsb=getOriginalArticle(link)
    //println("tOsb->${tOsb.toString()}")
    val LA= splitLongText(tOsb)
    //println("LA SIZE ${LA.size}")
    var lasize=0
    LA.forEach {
        lasize+=it.length
      //  println("LA->${it.length} $it")
        val ls=it.split(". ")
        lOriginalTrans.addAll(
            translateListStrings(
                ls,
                olang,
                tlang
            )
        )
    }
    //println("tosb  ${tOsb.length}  LA  $lasize")
    //lOriginalTrans.forEach {
    //    println(it.original)
    //    println(it.translated)
    //}
    return lOriginalTrans
    //val l= mutableListOf<OriginalTrans>()
    //return l
}

fun addStringWithEndPoint(str:String, textsb:StringBuilder){
    if(str.isEmpty()) return
    textsb.append(str)
    if(str[str.length-1]=='.')  { println("acaba en punt"); textsb.append(' ') }
    else { println("no acaba en punt"); textsb.append(". ") }
}

private fun splitLongText(text:StringBuilder):List<String>{
    val maxlen=3000
    val resultList= mutableListOf<String>()
    var LS = mutableListOf<String>()
    if(text.length<maxlen) { LS.add(text.toString()); return LS}


    LS= text.split(". ") as MutableList<String>
    val bs=StringBuilder()
    while(LS.size>0){
        val txt=LS.removeAt(0)
        if((txt.length+bs.length)<maxlen){
            bs.append(txt)
            bs.append(". ")
        }else{
            //bs.append(". ")
            resultList.add(bs.toString())
            bs.clear()
            bs.append(txt)
            bs.append(". ")
            if(text[txt.length].equals("."))
                bs.append("x. ")
        }
    }
    if(bs.length>0) resultList.add(bs.toString())
    return resultList
}

fun translateListStrings2(lOriginal:List<String>, name: String, tlang: String): List<OriginalTrans> {
    val iNewsPaper= P[name] ?: return emptyList()
    val olang=iNewsPaper.olang
    return translateListStrings(lOriginal, olang, tlang)
}


private fun translateListStrings(lOriginal:List<String>, olang: String, tlang: String): List<OriginalTrans> {
    return gettranslatedText(
        lOriginal.joinToString(". "),
        olang,
        tlang
    )
    println("caped translated")
    val dList= mutableListOf<OriginalTrans>(OriginalTrans("translated article $olang $tlang  ${LocalDateTime.now()}","fake"))
    return dList

    val apikey="AIzaSyBP1dsYp-jPF6PfVetJWcguNLiFouZ3mjo"
    val sUrl="https://www.googleapis.com/language/translate/v2?key=$apikey"
    val x= jsonTrans(lOriginal,olang,tlang)
    val rq= Json(JsonConfiguration.Stable).stringify(jsonTrans.serializer(),x)
    //Timber.d("URL: $sUrl")
    //Timber.d("json: $sjason")
    val cr= Jsoup.connect(sUrl)
        .header("Content-Type","application/json")
        .header("Accept","application/json")
        //.followRedirects(true)
        .ignoreContentType(true)
        .ignoreHttpErrors(true)
        .method(Connection.Method.POST)
        .requestBody(rq)
        .execute()
    val lTranslated= Json(JsonConfiguration.Stable).parse(Json4Kotlin_Base.serializer(),cr.body()).data.translations
    return lOriginal.zip(lTranslated){ a, b->OriginalTrans(a,b.translatedText)}
}
fun gettranslatedText(text: String, olang: String,tlang:String):List<OriginalTrans>  {
    val lOriginalTrans= mutableListOf<OriginalTrans>()
    if(text.length==0) return lOriginalTrans //????
    val url =
        "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + "$olang" + "&tl=" + "$tlang" + "&dt=t&q=" + URLEncoder.encode(
            text,
            "utf-8"
        )
    val d = Jsoup.connect(url).ignoreContentType(true).get().text()
    val lTrans=Json(JsonConfiguration.Stable).parseJson(d)
    //println("tlang->$tlang")

    if(lTrans.isNull) return lOriginalTrans    // si rebem un text buit la traduccio tambe ho sera millor a lentrada
    val qsm=lTrans.jsonArray[0] as JsonArray
    for(i in 0 until qsm.size){
        val l=qsm.getArray(i)
       // println("uno->${l[1]}")
       // println("cero->${l[0]}")
        lOriginalTrans.add(OriginalTrans(l[1].toString(),l[0].toString()))
    }
    return lOriginalTrans

}

/*
fun gettranslatedText(text: String, tlang: String,olang:String):MutableList<OriginalTrans>  {
    val url =
        "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + "$olang" + "&tl=" + "$tlang" + "&dt=t&q=" + URLEncoder.encode(
            text,
            "utf-8"
        )
    val d = Jsoup.connect(url).ignoreContentType(true).get().text()
    val t = JSONArray(d)
    val lOriginalTrans= mutableListOf<OriginalTrans>()
    val qsm = t.getJSONArray(0)
    for (i in 0 until qsm.length()) {
        val l = qsm.getJSONArray(i)
        val originalTrans=OriginalTrans(l.getString(1),l.getString(0))
        lOriginalTrans.add(originalTrans)
    }
    return lOriginalTrans
}
 */