package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajListuAnketa
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import java.time.LocalDateTime
import java.util.*

object AnketaRepository {

    private var mojeAnkete: ArrayList<Anketa> = arrayListOf()
    init {
        upisiMeNaAnketu("ETF istraživanje","Anketa 0")
        upisiMeNaAnketu("ETF istraživanje","O zagađenju")
        upisiMeNaAnketu("ETF istraživanje", "O obrazovanju")
        upisiMeNaAnketu("ETF istraživanje", "O online nastavi")
    }

    fun upisiMe(istrazivanje: String, grupa: String){
        var a: Anketa = getAll().stream().filter { ank -> ank.nazivIstrazivanja.equals(istrazivanje) &&
                ank.nazivGrupe.equals(grupa)}.findFirst().get()
        if (!mojeAnkete.contains(a))
            mojeAnkete.add(a)
    }
    fun upisiMeNaAnketu(istrazivanje: String, anketa: String){
        var a: Anketa = getAll().stream().filter { ank -> ank.nazivIstrazivanja.equals(istrazivanje) &&
                ank.naziv.equals(anketa)}.findFirst().get()
        if (!mojeAnkete.contains(a))
            mojeAnkete.add(a)
    }

  fun getMyAnkete():List<Anketa>{
      return mojeAnkete.ifEmpty { emptyList()};
    }
    fun getAll():List<Anketa>{
        return dajListuAnketa().ifEmpty { emptyList() }
    }
    fun getDone():List<Anketa>{
        var vrati= getMyAnkete().filter { anketa->anketa.datumRada!=null }
        return vrati.ifEmpty { emptyList() }
    }
    fun getFuture():List<Anketa>{
        val kalendar: Calendar = Calendar.getInstance()
        kalendar.set(LocalDateTime.now().year,LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
        val danasnjiDatum: Date =kalendar.time
        var vrati= getMyAnkete().filter { anketa->anketa.datumPocetak.after(danasnjiDatum) }
        return vrati.ifEmpty { emptyList() }
    }
    fun getNotTaken():List<Anketa>{
        val kalendar: Calendar = Calendar.getInstance()
        kalendar.set(LocalDateTime.now().year,LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
        val danasnjiDatum: Date =kalendar.time
        var vrati= getMyAnkete().filter { anketa->anketa.datumRada==null && anketa.datumKraj.before(danasnjiDatum) }
        return vrati.ifEmpty { emptyList() }
    }


}