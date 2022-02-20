package com.begemot.newspapers

import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.INewsPaper
import com.begemot.knewscommon.KArticle
import kotlinx.serialization.Serializable
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.text.StringBuilder

//@Serializable
object BLK: IBook {
    override val googleDir: String
        get() = "Bulgakov"
    override val olang: String
        get() = "ru"
    override val name: String
        get() = "Master i Margherita"
    override val desc: String
        get() = "Russian Book"
    override val logoName: String
        get() = "master.jpg"
    override val handler: String
        get() = "BLK"
    override val url: String
        get() = "https://en.wikipedia.org/wiki/The_Master_and_Margarita"
    override fun getOriginalHeadLines(): List<KArticle> {
        return listOf<KArticle>(
        KArticle("(1) Никогда не разговаривайте с неизвестными.","1"),
        KArticle("(2) Понтий Пилат.","2"),
        KArticle("(3) Седьмое доказательство.","3"),
        KArticle("(4) Погоня.","4"),
        KArticle("(5) Было дело в Грибоедове.","5"),
        KArticle("(6) Шизофрения, как и было сказано.","6"),
        KArticle("(7) Нехорошая квартирка.","7"),
        KArticle("(8) Поединок между профессором и поэтом.","8"),
        KArticle("(9) Коровьевские штуки.","9"),
        KArticle("(10) Вести из Ялты.","10"),
        KArticle("(11) Раздвоение Ивана.","11"),
        KArticle("(12) Черная магия и ее разоблачение.","12"),
        KArticle("(13) Явление героя.","13"),
        KArticle("(14) Слава петуху!.","14"),
        KArticle("(15) Сон Никанора Ивановича.","15"),
        KArticle("(16) Казнь.","16"),
        KArticle("(17) Беспокойный день.","17"),
        KArticle("(18) Неудачливые визитеры.","18"),
        KArticle("(19) Маргарита.","19"),
        KArticle("(20) Крем Азазелло.","20"),
        KArticle("(21) Полет.","21"),
        KArticle("(22) При свечах.","22"),
        KArticle("(23) Великий бал у сатаны.","23"),
        KArticle("(24) Извлечение мастера.","24"),
        KArticle("(25) Как прокуратор пытался спасти Иуду.","25"),
        KArticle("(26) Погребение.","26"),
        KArticle("(27) Конец квартиры N 50.","27"),
        KArticle("(28) Последние похождения Коровьева и Бегемота.","28"),
        KArticle("(29) Судьба мастера и Маргариты определена.","29"),
        KArticle("(30) Пора! Пора!.","30"),
        KArticle("(31) На Воробьевых горах.","31"),
        KArticle("(32) Прощение и вечный приют.","32"),
        KArticle("(33) Эпилог.","33")
        )
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