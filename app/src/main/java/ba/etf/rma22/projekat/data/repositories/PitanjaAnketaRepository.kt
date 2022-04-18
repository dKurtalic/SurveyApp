package ba.etf.rma22.projekat.data.repositories

import android.util.Log
import ba.etf.rma22.projekat.data.dajPitanjeAnkete
import ba.etf.rma22.projekat.data.dajSvaPitanja
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import kotlin.streams.toList

object PitanjaAnketaRepository {

    var mojiOdgovori:MutableMap<String,MutableList<String>> = mutableMapOf()

   fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje>{
       var listaPitanjeAnketa : List<PitanjeAnketa> =dajPitanjeAnkete().stream().filter { ankPitanje -> ankPitanje.anketa == nazivAnkete && ankPitanje.nazivIstrazivanja==nazivIstrazivanja}.toList()

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
    fun upisiOdgovor(nazivAnkete:String,odgovor:String,brojac:Int){
        if (mojiOdgovori.containsKey(nazivAnkete)){
            mojiOdgovori.getValue(nazivAnkete)[brojac] = odgovor
    }
        Log.v("ta","brojac:"+brojac)
       // for (i in mojiOdgovori.getValue(nazivAnkete)) Log.v("log","Logovi:"+i+" velicina: "+ mojiOdgovori.getValue(nazivAnkete).size)
    }
    fun otvorenaAnketa(anketa:Anketa){
        if (!mojiOdgovori.containsKey(anketa.naziv)) mojiOdgovori[anketa.naziv] = mutableListOf()
        for (i in 0..dajBrojPitanja(anketa.naziv,anketa.nazivIstrazivanja))
            mojiOdgovori.get(anketa.naziv)!!.add("")
    }
    fun dajOdgovoreZaAnketu(nazivAnkete:String):List<String>{
        var vrati:MutableList<String> = mutableListOf()
        if (mojiOdgovori.isNotEmpty()){
            if (mojiOdgovori.keys.contains(nazivAnkete)){
            var odgovoriAnkete= mojiOdgovori[nazivAnkete]
            if (odgovoriAnkete != null) {
                for (i in odgovoriAnkete) {
                    vrati.add(i)
                }
             }
          }
        }
        return vrati
    }
}
