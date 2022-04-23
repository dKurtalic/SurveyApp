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


    private var status:String=""
    init {
        dajStatusAnkete()
    }
    override fun equals(other: Any?): Boolean {
        return other is Anketa && this.naziv == other.naziv && this.nazivIstrazivanja == other.nazivIstrazivanja
    }
    fun IzracunajStatusAnkete():String{
            val kalendar: Calendar= Calendar.getInstance()
            kalendar.set(LocalDateTime.now().year, LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
            val danasnjiDatum:Date=kalendar.time
            if (datumRada!=null) status="plava"
            else if (datumPocetak.after(danasnjiDatum)) status="zuta"
            else if(datumPocetak.before(danasnjiDatum) &&
               datumKraj.after(danasnjiDatum))  status="zelena"
            else if (datumPocetak.before(danasnjiDatum) &&
               datumKraj.before(danasnjiDatum) && datumRada==null) status="crvena"
            return status
        }
    fun dajStatusAnkete():String {
        if (status=="") status=IzracunajStatusAnkete()
        return status
    }

    fun promijeniStatusAnkete(noviStatus:String){
        status=noviStatus
    }


}