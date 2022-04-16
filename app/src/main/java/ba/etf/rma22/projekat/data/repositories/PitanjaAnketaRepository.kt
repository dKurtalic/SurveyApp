package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajPitanjeAnkete
import ba.etf.rma22.projekat.data.dajSvaPitanja
import ba.etf.rma22.projekat.data.models.Pitanje
import kotlin.streams.toList

object PitanjaAnketaRepository {

   fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje>{
       var listaPitanjeAnketa=dajPitanjeAnkete().stream().filter { ankPitanje -> ankPitanje.anketa.equals(nazivAnkete) }.toList()
       var listaIDPitanja: MutableList<String> = mutableListOf()
       for (i in listaPitanjeAnketa){
           listaIDPitanja.add(i.naziv)
       }
       var pitanja= dajSvaPitanja().filter { ank->listaIDPitanja.contains(ank.naziv) }
       return pitanja
   }
}
