package com.begemot.newspapers

import com.begemot.knewscommon.KArticle
import com.begemot.translib.INewsPaper
import com.begemot.translib.splitLongText
import kotlinx.serialization.Serializable
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


object HLP : INewsPaper {
    override val olang: String
        get() = "en"
    override val name: String
        get() = "Help"
    override val desc: String
        get() = "Info about the app"
    override val logoName: String
        get() = "help.png"
    override val handler: String
        get() = "HLP"

    override fun getOriginalHeadLines(): List<KArticle> {
       val lHelp= mutableListOf<KArticle>()
       lHelp.add(KArticle("This Application is a simple tool aiming to help language students"))
       lHelp.add(KArticle("It displays the Headlines of the selected newspaper in the original and the current selected language"))
       return lHelp
    }

    override fun getOriginalArticle(link: String, strbuild: StringBuilder): List<String> {
        return emptyList()
    }

}