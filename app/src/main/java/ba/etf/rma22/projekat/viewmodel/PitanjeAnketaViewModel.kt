package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PitanjeAnketaViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getPitanja(idAnketa:Int,onSuccess: (List<Pitanje>)->Unit, onError: ()->Unit) {
        scope.launch {
            var pitanja= PitanjeAnketaRepository.getPitanja(idAnketa)!!
            when (pitanja){
                is List<Pitanje> -> onSuccess?.invoke(pitanja)
                else -> onError?.invoke()
            }
        }
    }
    fun dajBrojPitanja(idAnketa: Int, onSuccess: (Int) -> Unit, onError: () -> Unit) {
        scope.launch {
            var broj= PitanjeAnketaRepository.dajBrojPitanja(idAnketa)
            when (broj){
                is Int -> onSuccess?.invoke(broj)
                else -> onError?.invoke()
            }
        }
    }
      fun dajOdgovorZaPitanje(pitanje: Pitanje,anketa: Anketa, pokusaj:AnketaTaken,onSuccess:(Odgovor)->Unit, onError: () -> Unit) {
       scope.launch {
           var pitanja=PitanjeAnketaRepository.getPitanja(anketa.id)

           var odgovori=TakeAnketaRepository.getMojiOdgovori(pokusaj.id)
           var indeks=0
           for (i in pitanja!!){
               if (i.tekstPitanja==pitanje.tekstPitanja) break
               indeks++
           }
           var odgovor= odgovori!![indeks]
           when (odgovor){
               is Odgovor ->onSuccess?.invoke(odgovor)
               else -> onError?.invoke()
           }
       }
    }
    fun dajMojeOdgovoreZaAnketu(pokusaj: AnketaTaken,onSuccess:(List<Odgovor>)->Unit,onError: () -> Unit){
        scope.launch {
            var odgovori = TakeAnketaRepository.getMojiOdgovori(pokusaj.id)
            when(odgovori){
                is List<Odgovor> -> onSuccess?.invoke(odgovori)
                else -> onError?.invoke()
            }
        }
    }
    fun upisiOdgovor(anketa: Anketa, pokusaj: AnketaTaken, pitanje: Pitanje, odgovor:Int){
       scope.launch {
           OdgovorRepository.postaviOdgovorAnketa(pokusaj.id,pitanje.id,odgovor)
       }
    }

}