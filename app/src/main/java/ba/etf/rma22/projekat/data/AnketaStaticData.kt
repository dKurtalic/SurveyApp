package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*

fun dajListuAnketa():List<Anketa>{
 var datumi= dajSveDatume()

    return listOf(
        Anketa("Anketa 0","ETF istraživanje", datumi.get(6), datumi.get(7), datumi.get(5), 24, "Grupa0", 0.9F), //done korisnikova
        Anketa ("O zagađenju", "ETF istraživanje",  datumi.get(1), datumi.get(3), null, 100, "Grupa0",0F), //future korisnikova
        Anketa ("O obrazovanju", "ETF istraživanje",  datumi.get(4), datumi.get(2),  null,90, "Grupa0", 0F), // past korisnikova
        Anketa ("O online nastavi", "ETF istraživanje", datumi.get(2), datumi.get(1),null,19,"Grupa0",0F), //korisnikova active
        Anketa("Anketa1","PMF istraživanje", datumi.get(3),  datumi.get(3), null, 30,"Grupa0",0F), //future
        Anketa("Anketa PMF","PMF istraživanje", datumi.get(1),  datumi.get(3), null, 30,"Grupa1",0F), //future
        Anketa("Anketa urađena", "MF istraživanje",  datumi.get(0),  datumi.get(1),  datumi.get(2), 60, "Grupa2",0.5F), //done
        Anketa("Anketa MF", "MF istraživanje",  datumi.get(0),  datumi.get(1),  datumi.get(2), 60, "Grupa1",0.5F), //done
        Anketa("Anketa 2", "PFSA istraživanje", datumi.get(5),  datumi.get(5), null, 10, "Grupa3",0F), //past
        Anketa("Anketa PFSA", "PFSA istraživanje",  datumi.get(4),  datumi.get(0), null, 10, "Grupa2",0F), //past
        Anketa("Aktivna anketa", "Veliko istraživanje",  datumi.get(2),  datumi.get(1), null, 20, "Grupa4",0.71F), //active
        Anketa("Mala anketa", "Malo istraživanje",  datumi.get(4),  datumi.get(2),  datumi.get(0), 15,"Grupa5",0.33F),//done
    )

}
fun dajSveDatume():List<Date>{
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
    return listOf(prviDatum,drugiDatum,treciDatum,cetvrtiDatum,petiDatum,sestiDatum,sedmiDatum,osmiDatum)
}