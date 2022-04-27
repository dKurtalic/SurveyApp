package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajSvaPitanja
import ba.etf.rma22.projekat.data.dajSvaPitanjaPoIdu
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import kotlin.streams.toList

object PitanjeAnketaRepository {

    var mojiodgovori:MutableList<PitanjeAnketa> =mutableListOf()
    fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje>{
       val listaPitanjeAnketa : List<PitanjeAnketa> =dajSvaPitanjaPoIdu().stream().filter { ankPitanje -> ankPitanje.anketa == nazivAnkete && ankPitanje.nazivIstrazivanja==nazivIstrazivanja}.toList()

       val listaIDPitanja: MutableList<String> = mutableListOf()
       for (i in listaPitanjeAnketa){
           listaIDPitanja.add(i.naziv)
       }
       return dajSvaPitanja().filter { ank->listaIDPitanja.contains(ank.naziv) }
      }
    fun dajBrojPitanja(nazivAnkete:String,nazivIstrazivanja: String):Int{
        return getPitanja(nazivAnkete,nazivIstrazivanja).size
     }

    fun getMojiOdgovori(): MutableList<PitanjeAnketa> {
        return mojiodgovori
    }
    fun dajOdgovorZaPitanje(pitanje:Pitanje, anketa:Anketa):String{
        val nazivPitanja=pitanje.naziv
        val nazivAnkete=anketa.naziv
        val nazivIstrazivanja=anketa.nazivIstrazivanja
        val pom= mojiodgovori.stream().filter { p -> p.naziv==nazivPitanja && p.nazivIstrazivanja==nazivIstrazivanja
                && p.anketa==nazivAnkete}.findFirst()
        return if( pom.isPresent){
            pom.get().dajOdgovor()
        } else ""
     }

    fun dajMojeOdgovoreZaAnketu(anketa:Anketa):List<PitanjeAnketa>{
        val nazivAnkete=anketa.naziv

        return mojiodgovori.stream().filter { pa -> pa.anketa==nazivAnkete }.toList()
     }

    fun upisiOdgovor(anketa:Anketa, pitanje:Pitanje,odgovor: String){
        val pitanjeAnk=PitanjeAnketa(pitanje.naziv,anketa.naziv,anketa.nazivIstrazivanja)
      if (!mojiodgovori.contains(pitanjeAnk)){
            val pa=PitanjeAnketa(pitanje.naziv,anketa.naziv,anketa.nazivIstrazivanja)
            pa.postaviOdgovor(odgovor)
            mojiodgovori.add(pa)
         }
     }
}
