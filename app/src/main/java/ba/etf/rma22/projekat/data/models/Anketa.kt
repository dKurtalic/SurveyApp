package ba.etf.rma22.projekat.data.models

import java.time.LocalDateTime
import java.util.*

data class Anketa(
    val naziv: String,
    val nazivIstrazivanja: String,
    val datumPocetak: Date,
    val datumKraj: Date,
    val datumRada: Date?,
    val trajanje: Int,
    val nazivGrupe: String,
    val progres: Float,

) {
    override fun equals(other: Any?): Boolean {
        return other is Anketa && this.naziv.equals(other.naziv) && this.nazivIstrazivanja.equals(other.nazivIstrazivanja)
    }
    fun dajStatusAnkete():String{
            val kalendar: Calendar= Calendar.getInstance()
            kalendar.set(LocalDateTime.now().year, LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
            val danasnjiDatum:Date=kalendar.time
            if (datumRada!=null) return "plava"
            else if (datumPocetak.after(danasnjiDatum)) return "zuta"
            else if(datumPocetak.before(danasnjiDatum) &&
               datumKraj.after(danasnjiDatum)) return "zelena"
            else if (datumPocetak.before(danasnjiDatum) &&
               datumKraj.before(danasnjiDatum) && datumRada==null) return "crvena"
            return "zelena"
        }


}