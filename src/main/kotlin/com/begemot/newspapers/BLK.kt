package com.begemot.newspapers

import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.INewsPaper
import com.begemot.knewscommon.KArticle
import kotlinx.serialization.Serializable
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.text.StringBuilder

@Serializable
object BLK: IBook {
    override val directory: String
        get() = "Bulgakov"
    override val olang: String
        get() = "ru"
    override val name: String
        get() = "Master i Margherita"
    override val desc: String
        get() = "Russian Book"
    override val logoName: String
        get() = "master2.png"
    override val handler: String
        get() = "BLK"
    override val url: String
        get() = "https://en.wikipedia.org/wiki/The_Master_and_Margarita"

    override fun getOriginalHeadLines(): List<KArticle> {
        val lChapters=mutableListOf<KArticle>()
        lChapters.add(KArticle("(1) Никогда не разговаривайте с неизвестными.","1"))
        lChapters.add(KArticle("(2) Понтий Пилат.","2"))
        lChapters.add(KArticle("(3) Седьмое доказательство.","3"))
        lChapters.add(KArticle("(4) Погоня.","4"))
        lChapters.add(KArticle("(5) Было дело в Грибоедове.","5"))
        lChapters.add(KArticle("(6) Шизофрения, как и было сказано.","6"))
        lChapters.add(KArticle("(7) Нехорошая квартирка.","7"))
        lChapters.add(KArticle("(8) Поединок между профессором и поэтом.","8"))
        lChapters.add(KArticle("(9) Коровьевские штуки.","9"))
        lChapters.add(KArticle("(10) Вести из Ялты.","10"))
        lChapters.add(KArticle("(11) Раздвоение Ивана.","11"))
        lChapters.add(KArticle("(12) Черная магия и ее разоблачение.","12"))
        lChapters.add(KArticle("(13) Явление героя.","13"))
        lChapters.add(KArticle("(14) Слава петуху!.","14"))
        lChapters.add(KArticle("(15) Сон Никанора Ивановича.","15"))
        lChapters.add(KArticle("(16) Казнь.","16"))
        lChapters.add(KArticle("(17) Беспокойный день.","17"))
        lChapters.add(KArticle("(18) Неудачливые визитеры.","18"))
        lChapters.add(KArticle("(19) Маргарита.","19"))
        lChapters.add(KArticle("(20) Крем Азазелло.","20"))
        lChapters.add(KArticle("(21) Полет.","21"))
        lChapters.add(KArticle("(22) При свечах.","22"))
        lChapters.add(KArticle("(23) Великий бал у сатаны.","23"))
        lChapters.add(KArticle("(24) Извлечение мастера.","24"))
        lChapters.add(KArticle("(25) Как прокуратор пытался спасти Иуду.","25"))
        lChapters.add(KArticle("(26) Погребение.","26"))
        lChapters.add(KArticle("(27) Конец квартиры N 50.","27"))
        lChapters.add(KArticle("(28) Последние похождения Коровьева и Бегемота.","28"))
        lChapters.add(KArticle("(29) Судьба мастера и Маргариты определена.","29"))
        lChapters.add(KArticle("(30) Пора! Пора!.","30"))
        lChapters.add(KArticle("(31) На Воробьевых горах.","31"))
        lChapters.add(KArticle("(32) Прощение и вечный приют.","32"))
        lChapters.add(KArticle("(33) Эпилог.","33"))
        return lChapters
    }

    override fun getOriginalArticle(link: String): String {
        val p=Paths.get("src/main/resources/bul.txt")

        val lSentences= StringBuilder()//mutableListOf<String>()

        val lTxt=Files.readAllLines(p,Charset.forName("Windows-1251"))
        lTxt.forEachIndexed { i,it->
            if(it.isNotBlank() && (i>30)) {
                val j = it.split(Regex("(?<=\\. )"))
                val q= mutableListOf<String>()
                var sAux = ""
                j.forEach {
                    sAux += it
                    if(sAux.length>120){ q.add(sAux); sAux="" }
                }
                if(sAux.isNotEmpty()) q.add(sAux)
                lSentences.append(q.joinToString {""})
            }
        }
        println("fin")

      /*  lSentences.forEachIndexed { i, it ->
            println("$i (${it.length} -> $it ")
        }*/
        //val jsonSentences=lSentences.toJSON2()
        return lSentences.toString() //splitLongText(strb)

    }
}