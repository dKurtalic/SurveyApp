package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.listOfAllSurveys
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.promijeniPodatke
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.math.roundToInt


class FragmentPredaj: Fragment() {

    lateinit var anketa:Anketa
    lateinit var listaSvihAnketa:MutableList<Anketa>
    lateinit var pomoc:List<Anketa>
    companion object{
    fun newInstance(anketaa:Anketa, lista:List<Anketa>):FragmentPredaj=FragmentPredaj().apply {
        this.anketa=anketaa
        this.listaSvihAnketa=lista.toMutableList()
        this.pomoc=lista
        }
    }
    private var pitanjeAnketaVM=PitanjeAnketaViewModel()
    private lateinit var nazivAnkete:String
    private lateinit var nazivIstrazivanja:String
    private lateinit var progresTekst: TextView
    private lateinit var dugmePredaj: Button
    private var progres=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nazivAnkete=anketa.naziv
        nazivIstrazivanja=anketa.naziv
        listaSvihAnketa =pomoc.toMutableList()
        val view=inflater.inflate(R.layout.fragment_predaj,container, false)
        progresTekst=view.findViewById(R.id.progresTekst)
        dugmePredaj=view.findViewById(R.id.dugmePredaj)
        progresTekst.text= "$progres%"
        val status=anketa.dajStatusAnkete()
        if (status=="crvena" || status=="plava"){
            dugmePredaj.isEnabled=false
        }
        else {
            dugmePredaj.setOnClickListener { predajAnketu() }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val brojOdgovora= pitanjeAnketaVM.dajMojeOdgovoreZaAnketu(anketa).size
        val brojPitanja=pitanjeAnketaVM.dajBrojPitanja(anketa.naziv,anketa.nazivIstrazivanja)
        progres=izracunajProges(brojOdgovora.toDouble(),brojPitanja)
        progresTekst.text= "$progres%"
    }
    private fun izracunajProges(brojOdgovora: Double, brojPitanja: Int): Int{
        val broj:Double=(brojOdgovora/brojPitanja)*100.0
        val vrati=broj.toInt()
        val novi=vrati/20.0
        return (novi.roundToInt()*20)
    }

    private fun predajAnketu(){

        MainActivity.vpAdapter.removeAll()
        MainActivity.vpAdapter.add(0,FragmentAnkete())
        MainActivity.vpAdapter.add(1,FragmentPoruka.newInstance("Završili ste anketu $nazivAnkete u okviru istraživanja $nazivIstrazivanja"))
        MainActivity.viewPager.currentItem=1

        val localDate=LocalDate.now()
        val datum = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val indeks= listOfAllSurveys.indexOf(listOfAllSurveys.find { ank->ank==anketa })
        promijeniPodatke(indeks, Anketa(anketa.naziv,anketa.nazivIstrazivanja,anketa.datumPocetak,anketa.datumKraj,datum,anketa.trajanje,anketa.nazivGrupe,(progres/100.0).toFloat()))
        FragmentAnkete.anketaAdapter.updateAnkete(listOfAllSurveys)
    }

}