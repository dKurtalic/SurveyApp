package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.repositories.PitanjaAnketaRepository
import kotlin.math.roundToInt

class FragmentPredaj(anketaa: Anketa): Fragment() {
    var anketa=anketaa
    var nazivAnkete=anketaa.naziv
    var nazivIstrazivanja=anketaa.nazivIstrazivanja
    lateinit var progresTekst: TextView
    lateinit var dugmePredaj: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_predaj,container, false)
        progresTekst=view.findViewById(R.id.progresTekst)
        dugmePredaj=view.findViewById(R.id.dugmePredaj)
        var brojOdgovora= PitanjaAnketaRepository.dajOdgovoreZaAnketu(anketa.naziv).size
        var brojPitanja=PitanjaAnketaRepository.dajBrojPitanja(anketa.naziv,anketa.nazivIstrazivanja)
        var progres=izracunajProges(brojOdgovora.toDouble(),brojPitanja)
        progresTekst.text=progres.toInt().toString()+"%"
        var status=anketa.dajStatusAnkete()
        if (status=="crvena" || status=="plava"){
            dugmePredaj.isEnabled=false
        }
        else {
            dugmePredaj.setOnClickListener { predajAnketu() }
        }
        return view
    }

    private fun izracunajProges(brojOdgovora: Double, brojPitanja: Int): Int{
        var broj:Double=(brojOdgovora/brojPitanja)*100.0
        var vrati=broj.toInt()
        var novi=vrati/20.0
        return (novi.roundToInt()*20)
    }

    fun predajAnketu(){

        MainActivity.vpAdapter.removeAll()
        MainActivity.vpAdapter.add(0,FragmentAnkete())
        MainActivity.vpAdapter.add(1,FragmentPoruka("Završili ste anketu $nazivAnkete u okviru istraživanja $nazivIstrazivanja"))
        MainActivity.viewPager.currentItem=1
        anketa.promijeniStatusAnkete("plava")
        FragmentAnkete.anketaAdapter.notifyDataSetChanged()
    }
}