package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnketaViewModel {
 var offset=1
    val scope = CoroutineScope(Job() + Dispatchers.Main)


    fun getAll( onSuccess: ((ankete:List<Anketa>)->Unit)?, onError: () -> Unit ) {
        scope.launch {
          val result = AnketaRepository.getAll()
            when (result){
                is List<Anketa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun getDone( onSuccess: ((ankete:List<Anketa>)->Unit)?, onError: () -> Unit){
        scope.launch {
            val result = AnketaRepository.getDone()
            when (result){
                is List<Anketa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun getFuture( onSuccess: ((ankete:List<Anketa>)->Unit)?, onError: () -> Unit){
        scope.launch {
            val result = AnketaRepository.getFuture()
            when (result){
                is List<Anketa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun getNotTaken( onSuccess: ((ankete:List<Anketa>)->Unit)?, onError: () -> Unit){
        scope.launch {
            val result = AnketaRepository.getNotTaken()
            when (result){
                is List<Anketa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
    fun getMyAnkete( onSuccess: ((ankete:List<Anketa>)->Unit)?, onError: () -> Unit){
        scope.launch {
            val result = AnketaRepository.getUpisane()
            when (result){
                is List<Anketa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
   /* fun upisiMe( onSuccess: ((ankete:List<Anketa>)->Unit)?, onError: () -> Unit){
        scope.launch {
            getAll(onSuccess,onError)
        }
    }
    fun upisiMe(istrazivanje: String, grupa: String){
        return AnketaRepository.upisiMe(istrazivanje,grupa)
    }

    */


}