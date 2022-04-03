package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajListuAnketa
import ba.etf.rma22.projekat.data.models.Anketa
import java.time.LocalDateTime
import java.util.*

object AnketaRepository {

  fun getMyAnkete():List<Anketa>{
      return dajListuAnketa().ifEmpty { emptyList() }
    }
    fun getAll():List<Anketa>{
        return dajListuAnketa().ifEmpty { emptyList() }
    }
    fun getDone():List<Anketa>{
        var vrati= dajListuAnketa().filter { anketa->anketa.datumRada!=null }
        return vrati.ifEmpty { emptyList() }
    }
    fun getFuture():List<Anketa>{
        val kalendar: Calendar = Calendar.getInstance()
        kalendar.set(LocalDateTime.now().year,LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
        val danasnjiDatum: Date =kalendar.time
        var vrati= dajListuAnketa().filter { anketa->anketa.datumPocetak.after(danasnjiDatum) }
        return vrati.ifEmpty { emptyList() }
    }
    fun getNotTaken():List<Anketa>{
        val kalendar: Calendar = Calendar.getInstance()
        kalendar.set(LocalDateTime.now().year,LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
        val danasnjiDatum: Date =kalendar.time
        var vrati= dajListuAnketa().filter { anketa->anketa.datumRada==null && anketa.datumKraj.before(danasnjiDatum) }
        return vrati.ifEmpty { emptyList() }
    }


}