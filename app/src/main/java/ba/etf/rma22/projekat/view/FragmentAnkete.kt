package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import kotlinx.coroutines.*

class FragmentAnkete: Fragment() {
    companion object{
        var godina=0
        lateinit var anketaAdapter: AnketaAdapter
        fun newInstance():FragmentAnkete=FragmentAnkete()
    }
    var ankw=AnketaViewModel()
    var pawm=PitanjeAnketaViewModel()
    lateinit var sveAnkete:List<Anketa>
    private lateinit var listaAnketa: RecyclerView
    private var anketaViewModel= AnketaViewModel()
    private var spinner: Spinner? = null


    var anketaVM=AnketaViewModel()
    var pitanjaZaAnketu: List<Pitanje>? = null
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val view = inflater.inflate(R.layout.fragment_anketa, container, false)
            anketaAdapter = AnketaAdapter(arrayListOf()) { otvoriAnketu(it)}
            listaAnketa = view.findViewById(R.id.listaAnketa)
            listaAnketa.adapter = anketaAdapter
            listaAnketa.layoutManager = GridLayoutManager(view.context, 2, GridLayoutManager.VERTICAL, false)
             spinner=view.findViewById(R.id.filterAnketa)
            val opcije: List<String> = listOf("Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete")
            val adapterSpinner = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, opcije)
            spinner!!.adapter=adapterSpinner
             postaviSpinner()
            ankw.getAll(::onSuccess1, ::onError)
        return view

    }
    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                spinner!!.setSelection(1)
                filtrirajAnkete()
            }
        }
    }
     private fun otvoriAnketu(anketa: Anketa) {


        GlobalScope.launch {
            async {
                val status: String = anketa.dajStatusAnkete()
                var moje = emptyList<Anketa>()
                var mojeAnkete = AnketaRepository.getUpisane()
                if (mojeAnkete != null) moje = mojeAnkete
                withContext(Dispatchers.Main) {
                    if (status != "zuta" && moje.contains(anketa)) {

                        var pokusaj: AnketaTaken = TakeAnketaRepository.zapocniAnketu(anketa.id)!!

                        pitanjaZaAnketu = PitanjeAnketaRepository.getPitanja(anketa.id)


                        if (pitanjaZaAnketu?.isNotEmpty() == true) {
                            MainActivity.vpAdapter.removeAll()
                            var brojac = 0
                            if (pitanjaZaAnketu != null) {
                                for (i in pitanjaZaAnketu!!) {
                                    MainActivity.vpAdapter.add(
                                        brojac,
                                        FragmentPitanje.newInstance(
                                            i,
                                            anketa,
                                            brojac,
                                            sveAnkete,
                                            pokusaj
                                        )
                                    )
                                    brojac++
                                }
                                MainActivity.vpAdapter.add(
                                    brojac,
                                    FragmentPredaj.newInstance(anketa, sveAnkete, pokusaj)
                                )
                            }
                        }
                    }
                }
            }
        }
          }
    private fun onSuccess1(x:List<Anketa>){
        sveAnkete=x
    }

    private fun postaviSpinner(){
        spinner!!.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtrirajAnkete()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                filtrirajAnkete()
            }
        }
    }

    private fun filtrirajAnkete(){
        if (spinner!=null){
        when {
            spinner!!.selectedItem.toString()=="Sve moje ankete" -> anketaViewModel.getMyAnkete(::onSuccess,::onError)
            spinner!!.selectedItem.toString()=="Sve ankete" -> anketaViewModel.getAll(::onSuccess,::onError)
            spinner!!.selectedItem.toString()=="Urađene ankete" -> anketaViewModel.getDone(::onSuccess,::onError)
            spinner!!.selectedItem.toString()=="Buduće ankete" -> anketaViewModel.getFuture(::onSuccess,::onError)
            spinner!!.selectedItem.toString()=="Prošle ankete" -> anketaViewModel.getNotTaken(::onSuccess,::onError)
         }
        }
     }
    fun onSuccess(noveAnkete:List<Anketa>){
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                anketaAdapter.updateAnkete(noveAnkete)
            }
        }
    }
    fun onError(){
        var poruka= "Greška: problem s filtriranjem ili dobavljanjem podataka sa servera"
        Log.v("Lokacija: FragmentAnkete", poruka)
    }


}