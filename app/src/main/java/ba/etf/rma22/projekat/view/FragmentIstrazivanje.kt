package ba.etf.rma22.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FragmentIstrazivanje: Fragment() {
    companion object{
        fun newInstance():FragmentIstrazivanje=FragmentIstrazivanje()
    }
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var odabirIstrazivanja: Spinner
    private lateinit var istrazivanja: IstrazivanjeRepository
    private lateinit var grupe: GrupaRepository
    private lateinit var dodajIstrazivanjeDugme: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_istrazivanje,container, false)
        dodajIstrazivanjeDugme=view.findViewById(R.id.dodajIstrazivanjeDugme)
        odabirGodina=view.findViewById(R.id.odabirGodina)
        var opcijeGodine:List<String> = listOf("1","2","3","4","5")
        val adapterGodine = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, opcijeGodine)
        odabirGodina.adapter=adapterGodine
        odabirGodina.setSelection(FragmentAnkete.godina)

        odabirIstrazivanja=view.findViewById(R.id.odabirIstrazivanja)
        istrazivanja= IstrazivanjeRepository
        val adapterIstrazivanja = ArrayAdapter (view.context, android.R.layout.simple_list_item_1, istrazivanja.zaSpinner(odabirGodina.selectedItem.toString().toInt()))
        odabirIstrazivanja.adapter=adapterIstrazivanja

        odabirGrupa=view.findViewById(R.id.odabirGrupa)
        grupe= GrupaRepository
        val adapterGrupe = ArrayAdapter (view.context, android.R.layout.simple_list_item_1, grupe.getAllGroups())
        odabirGrupa.adapter=adapterGrupe

        odabirGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener  {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (odabirGodina.selectedItem.toString()!=null )  {
                    odabirIstrazivanja.isEnabled=true
                    dodajIstrazivanjeDugme.isEnabled=false
                    if (view != null) {
                        popuniSpinnerIstrazivanja(view.context)
                    }
                    if (view != null) {
                        popuniSpinnerGrupa(view.context)
                    }
                }
                else  {
                    odabirIstrazivanja.isEnabled=false
                    odabirGrupa.isEnabled=false
                    dodajIstrazivanjeDugme.isEnabled=false
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                popuniSpinnerIstrazivanja(view.context)
                popuniSpinnerGrupa(view.context)
                odabirIstrazivanja.isEnabled=false
                odabirGrupa.isEnabled=false
                dodajIstrazivanjeDugme.isEnabled=false
            }
        }
        odabirIstrazivanja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener  {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (odabirIstrazivanja.selectedItem.toString()!=null)  {
                    dodajIstrazivanjeDugme.isEnabled=false
                    odabirGrupa.isEnabled=true
                    if (view != null) {
                        popuniSpinnerGrupa(view.context)
                    }
                }
                else  {
                    odabirGrupa.isEnabled=false
                    dodajIstrazivanjeDugme.isEnabled=false
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                popuniSpinnerGrupa(view.context)
                odabirGrupa.isEnabled=false
                dodajIstrazivanjeDugme.isEnabled=false;
            }
        }
        odabirGrupa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener  {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                dodajIstrazivanjeDugme.isEnabled = odabirGrupa.selectedItem.toString()!=null
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                dodajIstrazivanjeDugme.isEnabled=false;
            }
        }
        dodajIstrazivanjeDugme.setOnClickListener {upisiMe(view.context)}
        return view
    }

    private fun popuniSpinnerIstrazivanja(context: Context) {
        var adapterIstrazivanja2 = ArrayAdapter(context, android.R.layout.simple_list_item_1, istrazivanja.zaSpinner(odabirGodina.selectedItem.toString().toInt()))
        odabirIstrazivanja.adapter=adapterIstrazivanja2
    }
    private fun popuniSpinnerGrupa(context:Context){
        var adapterGrupa2 = ArrayAdapter(context, android.R.layout.simple_list_item_1, grupe.getGroupsByIstrazivanje(odabirIstrazivanja.selectedItem.toString()))
        odabirGrupa.adapter=adapterGrupa2
    }
    private fun upisiMe(context:Context){
        FragmentAnkete.godina=odabirGodina.selectedItemPosition
        AnketaRepository.upisiMe(odabirIstrazivanja.selectedItem.toString(), odabirGrupa.selectedItem.toString())
        IstrazivanjeRepository.upisiMeNaIstrazivanje(odabirIstrazivanja.selectedItem.toString())
        GrupaRepository.upisiMe(odabirGrupa.selectedItem.toString())

        MainActivity.vpAdapter.refreshFragment(1,FragmentPoruka("Uspješno ste upisani u grupu: "+odabirGrupa.selectedItem.toString()+" istraživanja: "+ odabirIstrazivanja.selectedItem.toString()+"!"))
        }
}

