package com.begemot.newspapers

import com.begemot.knewscommon.KArticle
import com.begemot.knewscommon.INewsPaper


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
    override val url: String
        get() = "none"

    override fun getOriginalHeadLines(): List<KArticle> {
       val lHelp= mutableListOf<KArticle>()
       lHelp.add(KArticle("This Application is a simple tool aiming to help language students"))
       lHelp.add(KArticle("The student can read newspapers in both the original language and the translated at the same time"))
       lHelp.add(KArticle("It is also possible to read ful books following the same pattern"))
       lHelp.add(KArticle("The main goal is to keep text units as short as possible"))
       lHelp.add(KArticle("If you know more than one language you can take advantage of it by adding them to the list of known languages"))
       lHelp.add(KArticle("This will allow you to translate to different languages a piece of text allowing to refine the meaning of a sentence"))
       lHelp.add(KArticle("You can also listen to the text, and graduate tone and speed for any language"))
       lHelp.add(KArticle("More books and newspapers can be added on demand, feel free to make your proposals"))
       lHelp.add(KArticle("Not to mention it, thanks to Google translation and speech tools"))
       return lHelp
    }

    override fun getOriginalArticle(link: String): String {
        return ""
    }

}