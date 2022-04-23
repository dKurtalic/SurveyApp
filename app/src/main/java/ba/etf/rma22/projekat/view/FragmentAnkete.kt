package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel

class FragmentAnkete(): Fragment() {
    companion object{
        var godina=0
        lateinit var anketaAdapter: AnketaAdapter
        fun newInstance():FragmentAnkete=FragmentAnkete()
    }



    private lateinit var listaAnketa: RecyclerView
    private var anketaViewModel= AnketaViewModel()
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view=inflater.inflate(R.layout.fragment_anketa,container,false)
        anketaAdapter= AnketaAdapter(arrayListOf()){ otvoriAnketu(it)}
        listaAnketa=view.findViewById(R.id.listaAnketa)
        listaAnketa.adapter=anketaAdapter
        anketaAdapter.updateAnkete(anketaViewModel.getAll())
        listaAnketa.layoutManager=GridLayoutManager(view.context,2,GridLayoutManager.VERTICAL,false)
        spinner=view.findViewById(R.id.filterAnketa)
        val opcije: List<String> = listOf("Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete")
        val adapterSpinner = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, opcije)
        spinner.adapter=adapterSpinner
        postaviSpinner()
        spinner.setSelection(1)
        return view

    }
    private fun otvoriAnketu(anketa: Anketa) {
        var status=anketa.dajStatusAnkete()
        if (status!="zuta"){
            var pitanja= PitanjeAnketaRepository.getPitanja(anketa.naziv,anketa.nazivIstrazivanja)
            if(pitanja.isNotEmpty()){
                MainActivity.vpAdapter.removeAll()
                var brojac=0
                for (i in pitanja){
                    MainActivity.vpAdapter.add(brojac, FragmentPitanje(i,anketa,brojac))
                    brojac++
                }

                MainActivity.vpAdapter.add(brojac,FragmentPredaj(anketa))
                PitanjeAnketaRepository.otvorenaAnketa(anketa)

            }
        }

        //

    }

    private fun postaviSpinner(){
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtrirajAnkete()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                filtrirajAnkete()
            }
        }
    }

    private fun filtrirajAnkete():Unit{
        if (spinner.selectedItem.toString()=="Sve moje ankete") anketaAdapter.updateAnkete(
            AnketaRepository.getMyAnkete())
        else if (spinner.selectedItem.toString()=="Sve ankete") anketaAdapter.updateAnkete(
            AnketaRepository.getAll())
        else if (spinner.selectedItem.toString()=="Urađene ankete") anketaAdapter.updateAnkete(
            AnketaRepository.getDone())
        else if (spinner.selectedItem.toString()=="Buduće ankete") anketaAdapter.updateAnkete(
            AnketaRepository.getFuture())
        else if (spinner.selectedItem.toString()=="Prošle ankete") anketaAdapter.updateAnkete(
            AnketaRepository.getNotTaken())
    }



}