package com.begemot.translib

import com.begemot.knewscommon.*
import com.begemot.translib.MBAPE.P
import mu.KotlinLogging
//import org.json.JSONArray
private val logger = KotlinLogging.logger {}


fun getOriginalArticle(namepaper:String, link:String):String{
    //val sb=StringBuilder()
    val iNewsPaper= MBAPE.P[namepaper] ?: throw Exception("Wrong news paper name!! : $namepaper")
    return iNewsPaper.getOriginalArticle(link)
}


suspend fun  getTranslatedArticle(namepaper:String,tlang:String,link:String):List<OriginalTrans>{
     logger.debug { "getTranslatedArticle $link"}
     val iNewsPaper= P[namepaper] ?: throw Exception("Wrong news paper name!! : $namepaper")
     val sOriginalArticle=iNewsPaper.getOriginalArticle(link)
     return  transListOfParagraphs(sOriginalArticle,iNewsPaper.olang,tlang) //!!
}

suspend fun transListOfParagraphs(text:String,olang: String,tlang: String):List<OriginalTrans>{
    return try{
       //transPayParagraphs(text,olang,tlang)
       translateFreeListOfParagraphs(text,olang,tlang)
    }
    catch (e:Exception){
        transPayParagraphs(text,olang,tlang)
    }
}


suspend fun transPayParagraphs(text:String,olang: String,tlang: String):List<OriginalTrans>{
    logger.error{"-----PAY PAY PAY PAIN ------"}
    val lSentences = buildListSentences(text,olang)
    logger.debug { lSentences.print("List Sentences") }
    return transPayListOfParagraphs(lSentences,olang,tlang)
}



fun buildListSentences(text:String,olang: String):List<String>{
    if(olang.equals("zh")) return text.split(Regex("(?<=\\。)")).filterNot { it.isEmpty() }
    else  return text.split(Regex("(?<=\\. )")).filterNot { it.isEmpty() }
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
    logger.debug(LS.print("Sentences"))
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