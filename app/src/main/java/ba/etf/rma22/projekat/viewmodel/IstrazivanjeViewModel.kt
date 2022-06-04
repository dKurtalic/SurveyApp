package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class IstrazivanjeViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getIstrazivanjeByGodina (godina:Int,onSuccess: ((istrazivanja:List<Istrazivanje>)->Unit)?, onError: () -> Unit){
        scope.launch {
            val respond=IstrazivanjeIGrupaRepository.getIstrazivanjaPoGodinama(godina)
            when (respond){
                is List<Istrazivanje> -> onSuccess?.invoke(respond)
                else -> onError?.invoke()
            }
        }
    }
    fun getAll(onSuccess: ((istrazivanja:List<Istrazivanje>)->Unit)?, onError: () -> Unit){
        var svaIstrazivanja= mutableListOf<Istrazivanje>()
        scope.launch {
           var respond=IstrazivanjeIGrupaRepository.getIstrazivanja()
            when (respond){
                is List<Istrazivanje> -> onSuccess?.invoke(respond)
                else -> onError?.invoke()
            }
        }
    }
    /*
    ova ne bi trebalo da treba
    fun upisiMeNaIstrazivanje(nazivIstrazivanja:String){
       return IstrazivanjeRepository.upisiMeNaIstrazivanje(nazivIstrazivanja )
    }*/
    suspend fun popuniSpinner(god:Int) : ArrayList<Istrazivanje>{
            return (IstrazivanjeIGrupaRepository.zaSpinner(god) as ArrayList<Istrazivanje>?)!!

    }
    fun upisiUGrupu(gid:Int){
        scope.launch {
            IstrazivanjeIGrupaRepository.upisiUGrupu(gid)
        }
    }
    fun getMojaIstrazivanja(onSuccess:((istrazivanja:List<Istrazivanje>)->Unit)?, onError: () -> Unit) {
        scope.launch {
            var respond=IstrazivanjeIGrupaRepository.getMojaIstrazivanja()
            when (respond){
                is List<Istrazivanje> -> onSuccess?.invoke(respond)
                else -> onError?.invoke()
            }
        }
    }

}