package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository


class FragmentPitanje(pitanje: Pitanje, anketa: Anketa, brojac:Int): Fragment() {
    private var anketa=anketa
    private var pitanje=pitanje
    private var tekst=pitanje.tekst
    private var redniBroj=brojac
    private var odgovori:List<String> =pitanje.opcije
    private lateinit var tekstPitanja: TextView
    private lateinit var odgovoriLista: ListView
    private lateinit var dugmeZaustavi: Button

    private lateinit var view1:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MainActivity.viewPager.offscreenPageLimit=2
        Log.v("Fragment pitanje: ","podeseno na 100")
        view1=inflater.inflate(R.layout.fragment_pitanje,container, false)
        tekstPitanja=view1.findViewById(R.id.tekstPitanja)
        odgovoriLista=view1.findViewById(R.id.odgovoriLista)
        dugmeZaustavi=view1.findViewById(R.id.dugmeZaustavi)

        tekstPitanja.text=tekst

        var listAdapter=ListElementAdapter(view1.context,R.layout.custom_cell,odgovori,anketa,redniBroj,pitanje)

        odgovoriLista.adapter=listAdapter

        odgovoriLista.setOnItemClickListener { parent, view, position, id ->
            if (anketa.dajStatusAnkete()!="crvena" && anketa.dajStatusAnkete()!="plava"){
            var odg=view.findViewById<TextView>(R.id.answ)
                if (!PitanjeAnketaRepository.mojiodgovori.contains(PitanjeAnketa(pitanje.naziv,anketa.naziv,anketa.nazivIstrazivanja))){
                    listAdapter.obojiOdgovor(odg)
                    var pitanjee= PitanjeAnketa(pitanje.naziv, anketa.naziv, anketa.nazivIstrazivanja)
                    pitanjee.postaviOdgovor(odg.text.toString())
                    PitanjeAnketaRepository.upisiOdgovor(anketa, pitanje ,odg.text.toString())
                }

            }
        }
        dugmeZaustavi.setOnClickListener { prekiniAnketu() }
        return view1
        }

    private fun prekiniAnketu(){
        MainActivity.vpAdapter.removeAll()
        MainActivity.vpAdapter.add(0,FragmentAnkete())
        MainActivity.vpAdapter.add(1,FragmentIstrazivanje())
        MainActivity.viewPager.currentItem=0
    }

}