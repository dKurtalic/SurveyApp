package ba.etf.rma22.projekat

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UpisIstrazivanje:AppCompatActivity() {
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var odabirIstrazivanja: Spinner
    private lateinit var istrazivanja: IstrazivanjeRepository
    private lateinit var grupe: GrupaRepository
    private lateinit var dodajIstrazivanjeDugme: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upis_istrazivanje_layout)

        odabirGodina=findViewById(R.id.odabirGodina)
        var opcijeGodine:List<String> = listOf("1","2","3","4","5")
        val adapterGodine = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcijeGodine)
        odabirGodina.adapter=adapterGodine
        odabirGodina.setSelection(MainActivity.godina)

        odabirIstrazivanja=findViewById(R.id.odabirIstrazivanja)
        istrazivanja= IstrazivanjeRepository
        val adapterIstrazivanja = ArrayAdapter (this, android.R.layout.simple_list_item_1, istrazivanja.zaSpinner(odabirGodina.selectedItem.toString().toInt()))
        odabirIstrazivanja.adapter=adapterIstrazivanja


        odabirGrupa=findViewById(R.id.odabirGrupa)
        grupe= GrupaRepository
        val adapterGrupe = ArrayAdapter (this, android.R.layout.simple_list_item_1, grupe.getAllGroups())
        odabirGrupa.adapter=adapterGrupe

        odabirGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener  {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               if (odabirGodina.selectedItem.toString()!=null )  {
                   odabirIstrazivanja.isEnabled=true
                   popuniSpinnerIstrazivanja()
                   popuniSpinnerGrupa()
               }
                else  {
                    odabirIstrazivanja.isEnabled=false
                    odabirGrupa.isEnabled=false
               }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                    popuniSpinnerIstrazivanja()
                    popuniSpinnerGrupa()
                    odabirIstrazivanja.isEnabled=false
                    odabirGrupa.isEnabled=false
            }

        }
        odabirIstrazivanja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener  {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (odabirIstrazivanja.selectedItem.toString()!=null)  {
                    odabirGrupa.isEnabled=true
                    popuniSpinnerGrupa()
                }
                else  {
                    odabirGrupa.isEnabled=false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                popuniSpinnerGrupa()
                odabirGrupa.isEnabled=false
            }

        }

        dodajIstrazivanjeDugme=findViewById(R.id.dodajIstrazivanjeDugme)
        dodajIstrazivanjeDugme.setOnClickListener {upisiMe()}


    }
    private fun popuniSpinnerIstrazivanja() {
        var adapterIstrazivanja2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, istrazivanja.zaSpinner(odabirGodina.selectedItem.toString().toInt()))
        odabirIstrazivanja.adapter=adapterIstrazivanja2
    }
    private fun popuniSpinnerGrupa(){
        var adapterGrupa2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, grupe.getGroupsByIstrazivanje(odabirIstrazivanja.selectedItem.toString()))
        odabirGrupa.adapter=adapterGrupa2
    }
    private fun upisiMe(){
        MainActivity.godina=odabirGodina.selectedItemPosition
        AnketaRepository.upisiMe(odabirIstrazivanja.selectedItem.toString(), odabirGrupa.selectedItem.toString())
        IstrazivanjeRepository.upisiMeNaIstrazivanje(odabirIstrazivanja.selectedItem.toString())
        GrupaRepository.upisiMe(odabirGrupa.selectedItem.toString())
        Toast.makeText(this, "Uspješno ste upisani!", Toast.LENGTH_SHORT).show()
    }

}