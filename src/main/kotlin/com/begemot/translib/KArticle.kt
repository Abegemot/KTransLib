package com.begemot.translib

import com.begemot.knewscommon.*
import com.begemot.translib.MBAPE.P
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*
import org.jsoup.Jsoup
import java.net.URLEncoder
import kotlin.system.measureTimeMillis
import mu.KotlinLogging
//import org.json.JSONArray
private val logger = KotlinLogging.logger {}


fun getOriginalArticle(namepaper:String, link:String):List<String>{
    //val sb=StringBuilder()
    val iNewsPaper= MBAPE.P[namepaper] ?: throw Exception("Wrong news paper name!! : $namepaper")
    return iNewsPaper.getOriginalArticle(link)
}


fun  getTranslatedArticle(name:String,tlang:String,link:String):List<OriginalTrans>{
     logger.debug { "getTranslatedArticle $link"}
     val iNewsPaper= P[name] ?: return emptyList()
     //val str=StringBuilder()
     val LA=iNewsPaper.getOriginalArticle(link)
     logger.debug { LA.print("OriginalArticle ListOf Paragraphs ") }
   // return emptyList()
     return   translateListOfParagraphs(LA,iNewsPaper.olang,tlang) //!!
}

fun translateListOfParagraphs(lp:List<String>,olang: String,tlang: String):List<OriginalTrans>{
    logger.debug { "translateListOfParagraphs ${lp.size}  $olang $tlang" }
    val lOriginalTrans= mutableListOf<OriginalTrans>()
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


    LS= text.split(".") as MutableList<String>
    logger.debug("Number of senetences=${LS.size}")
    logger.debug(LS.print("Phrases"))
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

fun splitLongTextChinese(text:StringBuilder):List<String>{
    val maxlen=1800
    val resultList= mutableListOf<String>()
    var LS = mutableListOf<String>()
    if(text.length<maxlen) { LS.add(text.toString()); return LS}


    LS= text.split(" ").filter{ it.isNotEmpty() } as MutableList<String>
    logger.debug("Number of senetences=${LS.size}")
    logger.debug(LS.print("Phrases"))
    val bs=StringBuilder()
    while(LS.size>0){
        val txt=LS.removeAt(0)
        if((txt.length+bs.length)<maxlen){
            bs.append(txt)
            bs.append(" ")
        }else{
            //bs.append(". ")
            resultList.add(bs.toString())
            bs.clear()
            bs.append(txt)
            bs.append(" ")
            if(text[txt.length].equals("."))
                bs.append("x. ")
        }
    }
    if(bs.length>0) resultList.add(bs.toString())
    return resultList
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