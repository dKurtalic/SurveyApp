package ba.etf.rma22.projekat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.view.AnketaAdapter

import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
   companion object{
       var godina=0
   }

    private lateinit var listaAnketa: RecyclerView
    private lateinit var anketaAdapter: AnketaAdapter
    private var anketaViewModel= AnketaViewModel()
    private lateinit var spinner: Spinner
    private lateinit var upisDugme: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //postavljanje recyclerView-a
        listaAnketa=findViewById<RecyclerView>(R.id.listaAnketa)
        listaAnketa.layoutManager= GridLayoutManager(this,2,GridLayoutManager.VERTICAL, false)

        anketaAdapter= AnketaAdapter(listOf())
        listaAnketa.adapter=anketaAdapter
        anketaAdapter.updateAnkete(anketaViewModel.getAll())

        //spinner i filtriranje
        spinner=findViewById<Spinner>(R.id.filterAnketa)
        val opcije: List<String> = listOf("Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcije)
        spinner.adapter=adapter
        postaviSpinner()
        spinner.setSelection(1)

        //upisDugme
        upisDugme=findViewById(R.id.upisDugme)
        upisDugme.setOnClickListener { openUpisIstrazivanje() }

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

    private fun openUpisIstrazivanje() {
        var intent = Intent(this, UpisIstrazivanje::class.java)
        startActivity(intent)
    }


}