package ba.etf.rma22.projekat.viewmodel

import android.view.View
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OdgovorViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    fun postaviOdgovorAnketa(idAnketa:Int,pokusaj:AnketaTaken, idPitanje:Int, odgovor:Int){
        scope.launch {
           var odgg:Int?= OdgovorRepository.postaviOdgovorAnketa(pokusaj.id,idPitanje,odgovor)

        }
    }
    fun getOdgovoriAnketa(pokusaj:AnketaTaken, onSuccess:(List<Odgovor>)->Unit, onError: () -> Unit){
        scope.launch {
            var odgovori=TakeAnketaRepository.getMojiOdgovori(pokusaj)
            when (odgovori){
                is List<Odgovor> -> onSuccess?.invoke(odgovori)
                else -> onError?.invoke()
            }
        }

    }
}