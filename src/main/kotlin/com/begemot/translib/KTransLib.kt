package com.begemot.translib

import com.begemot.knewscommon.KArticle
import com.begemot.knewscommon.NewsPaper
import com.begemot.knewscommon.NewsPaperVersion
import com.begemot.newspapers.*



import com.begemot.knewscommon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import kotlin.system.measureTimeMillis


object  MBAPE{
    val version = 5
    val P=hashMapOf<String, INewsPaper>("GU" to GU,"RT" to RT,"SZ" to SZ,"LV" to LV
        ,"LM" to LM,"PCh" to PCh, "HLP" to HLP)

}

fun getNewsPapers():List<NewsPaper>{
    val lNP= mutableListOf<NewsPaper>()
    val l=ArrayList(MBAPE.P.values)
    l.remove(MBAPE.P["HLP"])
    l.forEach{
        lNP.add(it.toNewsPaper())
    }
    lNP.sortWith(compareBy({it.olang},{it.name}))
    lNP.add(HLP.toNewsPaper())
    return lNP
}

fun getNewsPapersIfChangedVersion(ver:Int):NewsPaperVersion{
    if(ver!=MBAPE.version){
        return NewsPaperVersion(MBAPE.version, getNewsPapers())
    }
    return NewsPaperVersion(0, emptyList())
}


/*fun addPinyinOTL(thl:List<OriginalTransLink>,original:Boolean):List<OriginalTransLink>{
//    thl.forEachIndexed { index, it ->  if(index<5) getPinying(it) }
    //val scope= CoroutineScope(Job()+Dispatchers.Default)
    lateinit var L:List<OriginalTransLink>

    val time = measureTimeMillis {
        runBlocking(Dispatchers.IO) {
            //val l = scope.launch {
            if(original)
            L = thl.pmap { it -> it.copy(romanized=ListPinyin(getPinying(it.kArticle.title))) }
            else
            L = thl.pmap { it -> it.copy(romanized=ListPinyin(getPinying(it.translated))) }
            //}
        }
    }
    println("addPinyinOTL pinyin time $time   total elements : ${thl.size}")
    return L

    //return thl.map { it->getPinying(it) }
}*/



fun HolaTransLib(){
    println("Hola trans lib")
}