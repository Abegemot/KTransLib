package com.begemot.translib

import com.begemot.knewscommon.KArticle
import com.begemot.newspapers.GU


interface INewsPaper{
    val olang     : String
    val nameFile  : String
    val logoName  : String
    // suspend fun getTranslatedArticle(originalTransLink: OriginalTransLink, statusApp: StatusApp):MutableList<OriginalTrans>
    fun getOriginalHeadLines():List<KArticle>
    fun getOriginalArticle(link:String,strbuild:StringBuilder):StringBuilder
    //fun getlines(lhd: MutableState<MutableList<OriginalTransLink>>, statusApp: StatusApp):Unit=get_HeadLines(lhd,statusApp, ::getHeadLines)
    // fun getName(e:Title):String
    // fun linkToArticleScreen():(otl:OriginalTransLink)->Screens=::FullArticle
    // fun linkToHeadLinesScreen():Screens=Screens.ListHeadLines
    // fun getArticle(originalTransLink:OriginalTransLink, trans: MutableState<MutableList<OriginalTrans>>, statusApp: StatusApp)= get_Article(originalTransLink,trans,statusApp,::getTranslatedArticle)
}




object  MBAPE{
    val P=hashMapOf<String, INewsPaper>("GU" to GU)

}


/*fun getTranslatedHeadLines(name:String,tlang:String){

}*/



fun HolaTransLib(){
    println("Hola trans lib")
}