package ba.etf.rma22.projekat.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel

class FragmentAnkete(): Fragment() {
    companion object{
        var godina=0
        fun newInstance():FragmentAnkete=FragmentAnkete()
    }


    private lateinit var anketaAdapter: AnketaAdapter
    private lateinit var listaAnketa: RecyclerView
    private var anketaViewModel= AnketaViewModel()
    private lateinit var spinner: Spinner
    var fragments= arrayListOf(this,FragmentIstrazivanje())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_anketa,container,false)
        anketaAdapter= AnketaAdapter(anketaViewModel.getAll())
        listaAnketa=view.findViewById(R.id.listaAnketa)
        listaAnketa.layoutManager=GridLayoutManager(view.context,2,GridLayoutManager.VERTICAL,false)
        listaAnketa.adapter=anketaAdapter


        spinner=view.findViewById(R.id.filterAnketa)
        val opcije: List<String> = listOf("Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete")
        val adapterSpinner = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, opcije)
        spinner.adapter=adapterSpinner
        postaviSpinner()
        spinner.setSelection(1)


        return view

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