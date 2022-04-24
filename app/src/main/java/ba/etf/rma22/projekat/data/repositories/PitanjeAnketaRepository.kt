package ba.etf.rma22.projekat.data.repositories

import android.util.Log
import ba.etf.rma22.projekat.data.dajSvaPitanja
import ba.etf.rma22.projekat.data.dajSvaPitanjaPoIdu
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import kotlin.streams.toList

object PitanjeAnketaRepository {

    var mojiOdgovori:MutableMap<String,MutableList<String>> = mutableMapOf()

    var mojiodgovori:MutableList<PitanjeAnketa> =mutableListOf()
   fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje>{
       var listaPitanjeAnketa : List<PitanjeAnketa> =dajSvaPitanjaPoIdu().stream().filter { ankPitanje -> ankPitanje.anketa == nazivAnkete && ankPitanje.nazivIstrazivanja==nazivIstrazivanja}.toList()

       var listaIDPitanja: MutableList<String> = mutableListOf()
       for (i in listaPitanjeAnketa){
           listaIDPitanja.add(i.naziv)
       }
       var pitanja= dajSvaPitanja().filter { ank->listaIDPitanja.contains(ank.naziv) }
       return pitanja
   }
    fun dajBrojPitanja(nazivAnkete:String,nazivIstrazivanja: String):Int{
        return getPitanja(nazivAnkete,nazivIstrazivanja).size
    }
    /*fun upisiOdgovor(nazivAnkete:String,odgovor:String,brojac:Int){
        if (mojiOdgovori.containsKey(nazivAnkete)){
            mojiOdgovori.getValue(nazivAnkete)[brojac] = odgovor
    }
        Log.v("ta","brojac:"+brojac)
    }

     */
    fun otvorenaAnketa(anketa:Anketa){
        if (!mojiOdgovori.containsKey(anketa.naziv)) mojiOdgovori[anketa.naziv] = mutableListOf()
        for (i in 0..dajBrojPitanja(anketa.naziv,anketa.nazivIstrazivanja))
            mojiOdgovori.get(anketa.naziv)!!.add("")
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

       var pom= mojiodgovori.stream().filter { pa -> pa.anketa==nazivAnkete }.toList()
        return pom

    }
    fun upisiOdgovor(anketa:Anketa, pitanje:Pitanje,odgovor: String){
        var pitanjeAnk=PitanjeAnketa(pitanje.naziv,anketa.naziv,anketa.nazivIstrazivanja)
      if (!mojiodgovori.contains(pitanjeAnk)){
            var anketa=anketa
            var pa=PitanjeAnketa(pitanje.naziv,anketa.naziv,anketa.nazivIstrazivanja)
            pa.postaviOdgovor(odgovor)
            mojiodgovori.add(pa)
        }
    }
   /* fun dajOdgovoreZaAnketu(nazivAnkete:String):List<String>{
        var vrati:MutableList<String> = mutableListOf()
        if (mojiOdgovori.isNotEmpty()){
            if (mojiOdgovori.keys.contains(nazivAnkete)){
            var odgovoriAnkete= mojiOdgovori[nazivAnkete]
            if (odgovoriAnkete != null) {
                for (i in odgovoriAnkete) {
                    if (i!="")
                    vrati.add(i)
                }
             }
          }
        }
        return vrati
    }
    */

}
