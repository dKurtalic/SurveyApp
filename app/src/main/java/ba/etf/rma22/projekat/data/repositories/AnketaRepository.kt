package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajListuAnketa
import ba.etf.rma22.projekat.data.listOfAllSurveys
import ba.etf.rma22.projekat.data.models.Anketa
import java.time.LocalDateTime
import java.util.*
import kotlin.streams.toList

object AnketaRepository {

   private var mojeAnkete: MutableList<Anketa> = mutableListOf()
    init {
        upisiMe("ETF istraživanje","Grupa0")
    }

    fun upisiMe(istrazivanje: String, grupa: String){
        val a = getAll().stream().filter { ank -> ank.nazivIstrazivanja == istrazivanje &&
                ank.nazivGrupe == grupa }.toList()
        for (ank in a){
        if (!mojeAnkete.contains(ank))
             mojeAnkete.add(ank)
        }
    }

    fun getMyAnkete():List<Anketa>{
        var konacnaLista= mutableListOf<Anketa>()
        var sveAnkete= listOfAllSurveys
        for (i in sveAnkete){
            for (j in mojeAnkete){
                if (i.naziv==j.naziv && i.nazivIstrazivanja==j.nazivIstrazivanja)
                    konacnaLista.add(i)
            }
        }
      return konacnaLista.ifEmpty { emptyList()}
    }
    fun getAll():List<Anketa>{
        return dajListuAnketa().ifEmpty { emptyList() }
    }
    fun getDone():List<Anketa>{
        val vrati= getMyAnkete().filter { anketa->anketa.datumRada!=null }
        return vrati.ifEmpty { emptyList() }
    }
    fun getFuture():List<Anketa>{
        val kalendar: Calendar = Calendar.getInstance()
        kalendar.set(LocalDateTime.now().year,LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
        val danasnjiDatum: Date =kalendar.time
        val vrati= getMyAnkete().filter { anketa->anketa.datumPocetak.after(danasnjiDatum) }
        return vrati.ifEmpty { emptyList() }
    }
    fun getNotTaken():List<Anketa>{
        val kalendar: Calendar = Calendar.getInstance()
        kalendar.set(LocalDateTime.now().year,LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
        val danasnjiDatum: Date =kalendar.time
        val vrati= getMyAnkete().filter { anketa->anketa.datumRada==null && anketa.datumKraj.before(danasnjiDatum) }
        return vrati.ifEmpty { emptyList() }
    }
}