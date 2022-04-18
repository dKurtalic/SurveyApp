package ba.etf.rma22.projekat.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.get
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.PitanjaAnketaRepository

class ListElementAdapter(context: Context,
                         @LayoutRes private val layoutResource: Int,
                         private var elements: List<String>,anketa: Anketa,trenutnaPozicija:Int
):
ArrayAdapter<String>(context, layoutResource, elements) {
    private var trenutnaPozicija=trenutnaPozicija
    private lateinit var answer:TextView
    var anketa=anketa
    override fun getView(position: Int, newView: View?, parent: ViewGroup): View {
        var view=newView
        view = LayoutInflater.from(context).inflate(R.layout.custom_cell, parent, false)
        answer=view.findViewById(R.id.answ)
        answer.text=elements[position]

       // var trenutnaPozicija=MainActivity.viewPager.currentItem
        Log.v("trenunta pozicija", trenutnaPozicija.toString())
        if (PitanjaAnketaRepository.dajOdgovoreZaAnketu(anketa.naziv).size>trenutnaPozicija){
        if (PitanjaAnketaRepository.dajOdgovoreZaAnketu(anketa.naziv)[trenutnaPozicija] ==answer.text.toString())
            obojiOdgovor(answer)
        }

        return view
    }
     fun obojiOdgovor(p:View){
        var view1 = p as TextView
        var plava= view1.resources.getColor(R.color.plava)
        var black=view1.resources.getColor(R.color.black)
        if (p.currentTextColor!=plava) view1.setTextColor(plava)
        else view1.setTextColor(black)
    }

    fun oznaciOdgovorene(anketaNaziv:String,odgovori:List<String>) {
        var answers :List<String> = PitanjaAnketaRepository.dajOdgovoreZaAnketu(anketaNaziv)
        if (answers.contains(answer.text.toString())) {
            Log.v("eeee", answers.get(0))
           // obojiOdgovor(answer)
         }
       /* var brojac=0
        for (i in answers) {
            if (odgovori.contains(i)) {
              if(pogled.findViewById<ListView>(R.id.odgovoriLista).get(brojac).toString()==i)
                  obojiOdgovor(pogled.findViewById<ListView>(R.id.odgovoriLista).get(brojac))
                Log.v("novi", "Selektuje se $brojac. odgovor")
            }
            brojac++
        }

        */
    }

    override fun getCount(): Int {
        return elements.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): String? {
        return elements[position]
    }

}