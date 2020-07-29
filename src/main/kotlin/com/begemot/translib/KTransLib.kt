package com.begemot.translib

import com.begemot.knewscommon.KArticle
import com.begemot.knewscommon.NewsPaper
import com.begemot.newspapers.GU
import com.begemot.newspapers.RT


interface INewsPaper{
    val olang     : String
    val nameFile  : String
    val logoName  : String
    val handler   : String
    // suspend fun getTranslatedArticle(originalTransLink: OriginalTransLink, statusApp: StatusApp):MutableList<OriginalTrans>
    fun getOriginalHeadLines():List<KArticle>
    fun getOriginalArticle(link:String,strbuild:StringBuilder):List<String> //the article split in 3000 chars pieces
    //fun getlines(lhd: MutableState<MutableList<OriginalTransLink>>, statusApp: StatusApp):Unit=get_HeadLines(lhd,statusApp, ::getHeadLines)
    // fun getName(e:Title):String
    // fun linkToArticleScreen():(otl:OriginalTransLink)->Screens=::FullArticle
    // fun linkToHeadLinesScreen():Screens=Screens.ListHeadLines
    // fun getArticle(originalTransLink:OriginalTransLink, trans: MutableState<MutableList<OriginalTrans>>, statusApp: StatusApp)= get_Article(originalTransLink,trans,statusApp,::getTranslatedArticle)
}




object  MBAPE{
    val P=hashMapOf<String, INewsPaper>("GU" to GU,"RT" to RT)

}

fun getNewsPapers():List<NewsPaper>{
    val lNP= mutableListOf<NewsPaper>()
    val l=ArrayList(MBAPE.P.values)
    l.forEach{
        lNP.add(NewsPaper(handler = it.handler,name=it.nameFile,title = "",oland = it.olang,logoname = it.logoName))
    }
   return lNP
}


/*fun getTranslatedHeadLines(name:String,tlang:String){

}*/



fun HolaTransLib(){
    println("Hola trans lib")
}