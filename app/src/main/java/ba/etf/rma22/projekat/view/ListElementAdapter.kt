package ba.etf.rma22.projekat.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import kotlinx.coroutines.*

class ListElementAdapter(context: Context,
                         @LayoutRes private val layoutResource: Int,
                         private var elements: List<String>,anketa: Anketa,trenutnaPozicija:Int,
                         pitanje: Pitanje,pokusaj: AnketaTaken
):
ArrayAdapter<String>(context, layoutResource, elements) {
    private var trenutnaPozicija=trenutnaPozicija
    private lateinit var answer:TextView
    private var pitanje=pitanje
    private var pokusaj=pokusaj

    var anketa=anketa
    var pitanjeAnketaVM=PitanjeAnketaViewModel()
    override fun getView(position: Int, newView: View?, parent: ViewGroup): View {
        var view=newView
        view = LayoutInflater.from(context).inflate(R.layout.custom_cell, parent, false)
        answer=view.findViewById(R.id.answ)
        answer.text=elements[position]

        GlobalScope.launch {
            var pom:Int?=-1
            async {
                pom=PitanjeAnketaRepository.dajOdgovorZaPitanje(pitanje,anketa,pokusaj)
                withContext(Dispatchers.Main){
                    if (pom!=null && position == pom){
                       obojiOdgovor(answer)
                    }
                }
            }

            }

        return view
    }
     fun obojiOdgovor(p:View){
        var view1 = p as TextView
        var plava= view1.resources.getColor(R.color.plava)
        view1.setTextColor(plava)
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