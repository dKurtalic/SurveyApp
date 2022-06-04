package ba.etf.rma22.projekat.viewmodel

import android.util.Log
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import kotlinx.coroutines.*
import kotlin.reflect.KFunction0

class GrupaViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getGroupsByIstrazivanje(id:Int, onSuccess:(List<Grupa>)->Unit, onError: () -> Unit){
       scope.launch {
           val result=IstrazivanjeIGrupaRepository.getGrupeZaIstrazivanje(id)
           when(result){
               is List<Grupa> -> onSuccess?.invoke(result)
               else -> onError?.invoke()
           }
       }
    }
    fun upisiMe(idGrupe:Int,onSuccess: (Boolean)->Unit, onError: () -> Unit) {
       scope.launch {
           val result  = IstrazivanjeIGrupaRepository.upisiUGrupu(idGrupe)
            when(result){
                is Boolean -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
       }
    }
    fun getAllGroups(onSuccess: ((ankete:List<Grupa>)->Unit)?, onError: () -> Unit) {
        scope.launch {
            when(val result=IstrazivanjeIGrupaRepository.getGrupe()){
                is List<Grupa> ->  onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
}