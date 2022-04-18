package ba.etf.rma22.projekat.view

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.PitanjaAnketaRepository


class FragmentPitanje(tekstStr:String, odgovoriLista:List<String>,anketa: Anketa,brojac:Int): Fragment() {
    private var anketa=anketa
    private var anketaNaziv=anketa.naziv
    private var tekst=tekstStr
    private var redniBroj=brojac
    private var odgovori:List<String> =odgovoriLista
    private lateinit var tekstPitanja: TextView
    private lateinit var odgovoriLista: ListView
    private lateinit var dugmeZaustavi: Button

    private lateinit var view1:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1=inflater.inflate(R.layout.fragment_pitanje,container, false)
        tekstPitanja=view1.findViewById(R.id.tekstPitanja)
        odgovoriLista=view1.findViewById(R.id.odgovoriLista)
        dugmeZaustavi=view1.findViewById(R.id.dugmeZaustavi)

        tekstPitanja.text=tekst

        var listAdapter=ListElementAdapter(view1.context,R.layout.custom_cell,odgovori,anketa,redniBroj)

        odgovoriLista.adapter=listAdapter

        odgovoriLista.setOnItemClickListener { parent, view, position, id ->
            if (anketa.dajStatusAnkete()!="crvena" && anketa.dajStatusAnkete()!="plava"){
            var odg=view.findViewById<TextView>(R.id.answ)
            listAdapter.obojiOdgovor(odg)
            PitanjaAnketaRepository.upisiOdgovor(anketaNaziv,odg.text.toString(),redniBroj)
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