package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*

fun dajListuAnketa():List<Anketa>{
    val kalendar: Calendar= Calendar.getInstance()
    kalendar.set(2006,11,5)
    val prviDatum: Date=kalendar.time
    kalendar.set(2025,3,1)
    val drugiDatum:Date=kalendar.time
    kalendar.set(2020,1,1)
    val treciDatum:Date=kalendar.time
    kalendar.set(2030,1,2)
    val cetvrtiDatum:Date=kalendar.time
    kalendar.set(2002,6,5)
    val petiDatum:Date=kalendar.time
    kalendar.set(2019,11,9)
    val sestiDatum:Date=kalendar.time
    kalendar.set(2016,10,21)
    val sedmiDatum:Date=kalendar.time
    kalendar.set(2020,8,12)
    val osmiDatum:Date=kalendar.time
    /*
    buducnost, uradjena, nije uradjena i prosla, nije uradjena i u toku
     */
    return listOf(
        Anketa("Anketa 0","ETF istraživanje", petiDatum, drugiDatum, prviDatum, 24, "Grupa0", 0.9F), //done
        Anketa("Anketa1","PMF istraživanje",cetvrtiDatum, cetvrtiDatum, null, 30,"Grupa1",0F), //future
        Anketa("Anketa urađena", "MF istraživanje", prviDatum, drugiDatum, treciDatum, 60, "Grupa2",0.5F), //done
        Anketa("Anketa 2", "PFSA istraživanje", petiDatum, prviDatum, null, 10, "Grupa3",0F), //past
        Anketa("Aktivna anketa", "Veliko istraživanje", treciDatum, drugiDatum, null, 20, "Grupa4",0F), //active
        Anketa("Mala anketa", "Malo istraživanje", petiDatum, treciDatum, prviDatum, 15,"Grupa5",0.33F),//done

    )
}