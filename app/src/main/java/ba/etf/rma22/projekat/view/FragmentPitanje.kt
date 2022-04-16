package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.R

class FragmentPitanje(tekstStr:String, odgovoriLista:List<String>): Fragment() {
    private var tekst=tekstStr
    private var odgovori:List<String> =odgovoriLista
    private lateinit var tekstPitanja: TextView
    private lateinit var odgovoriLista: ListView
    private lateinit var dugmeZaustavi: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_pitanje,container, false)
        tekstPitanja=view.findViewById(R.id.tekstPitanja)
        odgovoriLista=view.findViewById(R.id.odgovoriLista)
        dugmeZaustavi=view.findViewById(R.id.dugmeZaustavi)

        tekstPitanja.text=tekst
        var listAdapter= ArrayAdapter(view.context, android.R.layout.simple_list_item_1,odgovori)
        odgovoriLista.adapter=listAdapter
        return view
    }
}