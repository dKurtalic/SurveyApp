package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.dajListuAnketa
import java.time.LocalDateTime
import java.util.*

class AnketaViewModel {
    fun dajAnkete():List<Anketa>{
        return AnketaRepository.getMyAnkete()
    }
    fun getMyAnkete():List<Anketa>{
        return dajListuAnketa()
    }
    fun getAll():List<Anketa>{
        return dajListuAnketa()
    }
    fun getDone():List<Anketa>{
        return dajListuAnketa().filter { anketa->anketa.datumRada!=null }
    }
    fun getFuture():List<Anketa>{
        val kalendar: Calendar = Calendar.getInstance()
        kalendar.set(LocalDateTime.now().year, LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
        val danasnjiDatum: Date =kalendar.time
        return dajListuAnketa().filter { anketa->anketa.datumPocetak.after(danasnjiDatum) }
    }
    fun getNotTaken():List<Anketa>{
        val kalendar: Calendar = Calendar.getInstance()
        kalendar.set(LocalDateTime.now().year, LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
        val danasnjiDatum: Date =kalendar.time
        return dajListuAnketa().filter { anketa->anketa.datumRada==null && anketa.datumKraj.before(danasnjiDatum) }
    }
}