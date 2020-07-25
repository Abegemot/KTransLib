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

fun getOriginalArticle(name:String,link:String):List<String>{
    val sb=StringBuilder()
    val iNewsPaper= MBAPE.P[name] ?: return emptyList()
    return iNewsPaper.getOriginalArticle(link,sb)
}


fun  getTranslatedArticle(name:String,tlang:String,link:String):List<OriginalTrans>{
    var lOriginalTrans: MutableList<OriginalTrans>

    val iNewsPaper= P[name] ?: return emptyList()

    val olang=iNewsPaper.olang
    val str=StringBuilder()
    val LA=iNewsPaper.getOriginalArticle(link,str)

    lOriginalTrans= translateListOfParagraphs(LA,name,tlang).toMutableList() //!!

    return lOriginalTrans
}

fun translateListOfParagraphs(lp:List<String>,namepaper:String,tlang: String):List<OriginalTrans>{
    println("translateListOfParagraphs ${lp.size}  $namepaper $tlang")
    val lOriginalTrans= mutableListOf<OriginalTrans>()
    val iNewsPaper= P[namepaper] ?: return emptyList()
    val olang=iNewsPaper.olang
    lp.forEach {
        lOriginalTrans.addAll(gettranslatedText(it,olang,tlang))
    }
    return lOriginalTrans
}

fun addStringWithEndPoint(str:String, textsb:StringBuilder){
    val debug=false
    if(str.isEmpty()) return
    textsb.append(str)
    if(str[str.length-1]=='.')  { if(debug) println("acaba en punt"); textsb.append(' ') }
    else { if(debug) println("no acaba en punt"); textsb.append(". ") }
}

fun splitLongText(text:StringBuilder):List<String>{
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