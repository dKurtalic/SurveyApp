package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*

fun dajListuAnketa():List<Anketa>{
    val kalendar: Calendar= Calendar.getInstance()
    kalendar.set(2006,6,5)
    val prviDatum: Date=kalendar.time
    kalendar.set(2025,6,1)
    val drugiDatum:Date=kalendar.time
    kalendar.set(2020,7,1)
    val treciDatum:Date=kalendar.time
    kalendar.set(2030,7,2)
    val cetvrtiDatum:Date=kalendar.time
    kalendar.set(2002,6,5)
    val petiDatum:Date=kalendar.time
    kalendar.set(2019,3,5)
    val sestiDatum:Date=kalendar.time
    kalendar.set(2016,1,2)
    val sedmiDatum:Date=kalendar.time
    kalendar.set(2020,12,12)
    val osmiDatum:Date=kalendar.time


    return listOf(
        Anketa("Anketa 0","ETF istraživanje", sedmiDatum, osmiDatum, sestiDatum, 24, "Grupa0", 0.9F), //done korisnikova
        Anketa ("O zagađenju", "ETF istraživanje", drugiDatum, cetvrtiDatum, null, 100, "Grupa1",0F), //future korisnikova
        Anketa ("O obrazovanju", "ETF istraživanje", petiDatum,treciDatum,  null,90, "Grupa0", 0F), // past korisnikova
        Anketa ("O online nastavi", "ETF istraživanje",treciDatum,drugiDatum,null,19,"Grupa1",0F), //korisnikova active
        Anketa("Anketa1","PMF istraživanje",cetvrtiDatum, cetvrtiDatum, null, 30,"Grupa0",0F), //future
        Anketa("Anketa PMF","PMF istraživanje",drugiDatum, cetvrtiDatum, null, 30,"Grupa1",0F), //future
        Anketa("Anketa urađena", "MF istraživanje", prviDatum, drugiDatum, treciDatum, 60, "Grupa2",0.5F), //done
        Anketa("Anketa MF", "MF istraživanje", prviDatum, drugiDatum, treciDatum, 60, "Grupa1",0.5F), //done
        Anketa("Anketa 2", "PFSA istraživanje", sedmiDatum, sestiDatum, null, 10, "Grupa3",0F), //past
        Anketa("Anketa PFSA ", "PFSA istraživanje", petiDatum, prviDatum, null, 10, "Grupa2",0F), //past
        Anketa("Aktivna anketa", "Veliko istraživanje", treciDatum, drugiDatum, null, 20, "Grupa4",0.71F), //active
        Anketa("Mala anketa", "Malo istraživanje", petiDatum, treciDatum, prviDatum, 15,"Grupa5",0.33F),//done
    )
}