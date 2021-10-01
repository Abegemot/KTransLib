package com.begemot.newspapers

import com.begemot.knewscommon.IBook
import com.begemot.knewscommon.KArticle

object RougeEtNoire : IBook{
    override val googleDir: String
        get() = "Stendhal/RougeEtNoire"
    override val olang: String
        get() = "fr"
    override val name: String
        get() = "Le Rouge et le Noire"
    override val desc: String
        get() = "French Book"
    override val logoName: String
        get() = "rougeetnoir.jpg"
    override val url: String
        get() = "https://en.wikipedia.org/wiki/The_Red_and_the_Black"
    override val handler: String
        get() = "RN"

    override fun getOriginalHeadLines(): List<KArticle> {
        val lChapters= listOf<KArticle>(
            KArticle("I Une petite ville","1"),
            KArticle("II Un maire","2"),
            KArticle("III Le bien des pauvres","3"),
            KArticle("IV Un père et un fils","4"),
            KArticle("V Une négociation","5"),
            KArticle("VI L’ennui","6"),
            KArticle("VII Les affinités électives","7"),
            KArticle("VIII Petits événements","8"),
            KArticle("IX Une soirée à la campagne","9"),
            KArticle("X Un grand cœur et une petite fortune","10"),
            KArticle("XI Une soirée","11"),
            KArticle("XII Un voyage","12"),
            KArticle("XIII Les bas à jour","13"),
            KArticle("XIV Les ciseaux anglais","14"),
            KArticle("XV Le chant du coq","15"),
            KArticle("XVI Le lendemain","16"),
            KArticle("XVII Le premier adjoint","17"),
            KArticle("XVIII Un roi à Verrières","18"),
            KArticle("XIX Penser fait souffrir","19"),
            KArticle("XX Les lettres anonymes","20"),
            KArticle("XXI Dialogue avec un maître","21"),
            KArticle("XXII Façons d’agir en 1830","22"),
            KArticle("XXIII Chagrins d’un fonctionnaire","23"),
            KArticle("XXIV Une capitale","24"),
            KArticle("XXV Le séminaire","25"),
            KArticle("XXVI Le monde ou ce qui manque au riche","26"),
            KArticle("XXVII Première expérience de la vie","27"),
            KArticle("XXVIII Une procession","28"),
            KArticle("XXIX Le premier avancement","29"),
            KArticle("XXX Un ambitieux","30"),
            KArticle("XXXI Les Plaisirs de la campagne","31"),
            KArticle("XXXII Entrée dans le Monde","32"),
            KArticle("XXXIII Les premiers pas","33"),
            KArticle("XXXIV L’Hôtel de La Mole","34"),
            KArticle("XXXV La Sensibilité est une grande dévote","35"),
            KArticle("XXXVI Manière de se prononcer","36"),
            KArticle("XXXVII. Une attaque de Goutte","37"),
            KArticle("XXXVIII. Quelle est la Décoration qui distingue","38"),
            KArticle("XXXIX. Le Bal","39"),
            KArticle("XL. La reine Marguerite","40"),
            KArticle("XLI. L’empire d’une jeune Fille","41"),
            KArticle("XLII. Serait-ce un Danton","42"),
            KArticle("XLIII. Un Complot","43"),
            KArticle("XLIV. Pensées d’une jeune Fille","44"),
            KArticle("XLV. Est-ce un Complot","45"),
            KArticle("XLVI. Une Heure du matin","46"),
            KArticle("XLVII. Une vieille Épée","47"),
            KArticle("XLVIII. Moments cruels","48"),
            KArticle("XLIX. L’Opéra Bouffe","49"),
            KArticle("L. Le Vase du Japon","50"),
            KArticle("LI. La Note secrète","51"),
            KArticle("LII. La Discussion","52"),
            KArticle("LIII. Le Clergé, les Bois, la Liberté","53"),
            KArticle("LIV. Strasbourg","54"),
            KArticle("LV. Le Ministère de la Vertu","55"),
            KArticle("LVI. L’Amour moral","56"),
            KArticle("LVII. Les plus belles Places de l’Église","57"),
            KArticle("LVIII. Manon Lescaut.","58"),
            KArticle("LIX. L’ennui","59"),
            KArticle("LX. Une Loge aux Bouffes","60"),
            KArticle("LXI. Lui faire peur","61"),
            KArticle("LXII. Le Tigre","62"),
            KArticle("LXIII. L’enfer de la Faiblesse","63"),
            KArticle("LXIV. Un homme d’esprit","64"),
            KArticle("LXV. Un Orage","65"),
            KArticle("LXVI. Détails tristes","66"),
            KArticle("LXVII. Un Donjon","67"),
            KArticle("LXVIII. Un Homme puissant","68"),
            KArticle("LXIX. L’Intrigue","69"),
            KArticle("LXX. La Tranquillité","70"),
            KArticle("LXXI. Le Jugement","71"),
            KArticle("LXXII. ","72"),
            KArticle("LXXIII ","73"),
            KArticle("LXXIV ","74"),
            KArticle("LXXV null","75"),
        )
        return lChapters
    }
}