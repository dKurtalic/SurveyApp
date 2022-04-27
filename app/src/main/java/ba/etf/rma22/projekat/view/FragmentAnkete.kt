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
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.dajListuAnketa
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel

class FragmentAnkete: Fragment() {
    companion object{
        var godina=0
        lateinit var anketaAdapter: AnketaAdapter
        fun newInstance():FragmentAnkete=FragmentAnkete()
    }

    private lateinit var listaAnketa: RecyclerView
    private var anketaViewModel= AnketaViewModel()
    private lateinit var spinner: Spinner
    private var pitanjeAnketaVM=PitanjeAnketaViewModel()

    var anketaVM=AnketaViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_anketa,container,false)
        anketaAdapter= AnketaAdapter(arrayListOf()){ otvoriAnketu(it)}
        listaAnketa=view.findViewById(R.id.listaAnketa)
        listaAnketa.adapter=anketaAdapter
        anketaAdapter.updateAnkete(dajListuAnketa())
        listaAnketa.layoutManager=GridLayoutManager(view.context,2,GridLayoutManager.VERTICAL,false)
        spinner=view.findViewById(R.id.filterAnketa)
        val opcije: List<String> = listOf("Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete")
        val adapterSpinner = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, opcije)
        spinner.adapter=adapterSpinner
        postaviSpinner()
        spinner.setSelection(1)
        return view
    }
    override fun onResume() {
        super.onResume()
        anketaAdapter.updateAnkete(dajListuAnketa())
        spinner.setSelection(1)
    }
    private fun otvoriAnketu(anketa: Anketa) {
        val status=anketa.dajStatusAnkete()

        if (status!="zuta" && anketaVM.getMyAnkete().contains(anketa)){
            val pitanja= pitanjeAnketaVM.getPitanja(anketa.naziv,anketa.nazivIstrazivanja)
            if(pitanja.isNotEmpty()){
                MainActivity.vpAdapter.removeAll()
                var brojac=0
                for (i in pitanja){
                    MainActivity.vpAdapter.add(brojac, FragmentPitanje.newInstance(i,anketa,brojac,
                        dajListuAnketa()))
                    brojac++
                }
                MainActivity.vpAdapter.add(brojac,FragmentPredaj.newInstance(anketa, dajListuAnketa()))
            }
        }
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

    private fun filtrirajAnkete(){
        when {
            spinner.selectedItem.toString()=="Sve moje ankete" -> anketaAdapter.updateAnkete(
                anketaViewModel.getMyAnkete())
            spinner.selectedItem.toString()=="Sve ankete" -> anketaAdapter.updateAnkete(
                anketaViewModel.getAll())
            spinner.selectedItem.toString()=="Urađene ankete" -> anketaAdapter.updateAnkete(
                anketaViewModel.getDone())
            spinner.selectedItem.toString()=="Buduće ankete" -> anketaAdapter.updateAnkete(
                anketaViewModel.getFuture())
            spinner.selectedItem.toString()=="Prošle ankete" -> anketaAdapter.updateAnkete(
                anketaViewModel.getNotTaken())
         }
     }
}