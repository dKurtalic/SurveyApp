package ba.etf.rma22.projekat.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import ba.etf.rma22.projekat.viewmodel.GrupaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*


class FragmentIstrazivanje: Fragment() {
    companion object{
        fun newInstance():FragmentIstrazivanje=FragmentIstrazivanje()
    }
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var odabirIstrazivanja: Spinner
    private lateinit var dodajIstrazivanjeDugme: FloatingActionButton

   private var istrazivanjaVM=IstrazivanjeViewModel()
   private var grupeVM=GrupaViewModel()
   private var anketeVM= AnketaViewModel()
    lateinit var trazeneGrupe:List<Grupa>
     var svaIstrazivanja:List<Istrazivanje> = listOf()
   private lateinit var view1:View
   var trenutnaGrupa:Grupa? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

                view1 = inflater.inflate(R.layout.fragment_istrazivanje, container, false)
                dodajIstrazivanjeDugme = view1.findViewById(R.id.dodajIstrazivanjeDugme)
                odabirGodina = view1.findViewById(R.id.odabirGodina)
                var opcijeGodine: List<String> = listOf("1", "2", "3", "4", "5")
                val adapterGodine =
                    ArrayAdapter(view1.context, android.R.layout.simple_list_item_1, opcijeGodine)
                odabirGodina.adapter = adapterGodine
                odabirGodina.setSelection(FragmentAnkete.godina)
                odabirGrupa = view1.findViewById(R.id.odabirGrupa)
                odabirIstrazivanja=view1.findViewById(R.id.odabirIstrazivanja)


            odabirGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (odabirGodina.selectedItem.toString() != null) {
                    odabirIstrazivanja.isEnabled = true
                    popuniSpinnerIstrazivanja()
                    popuniSpinnerGrupa()

                } else {
                    odabirIstrazivanja.isEnabled = false
                    odabirGrupa.isEnabled = false
                    dodajIstrazivanjeDugme.isEnabled = false
                }
            /*    dodajIstrazivanjeDugme.isEnabled =odabirGodina.selectedItem.toString() != "" && odabirIstrazivanja.selectedItem.toString() != "" && odabirGrupa.selectedItem.toString() != ""
             */
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                popuniSpinnerIstrazivanja()
             /*   dodajIstrazivanjeDugme.isEnabled =odabirGodina.selectedItem.toString() != "" && odabirIstrazivanja.selectedItem.toString() != "" && odabirGrupa.selectedItem.toString() != ""
              */
            }
        }


        odabirIstrazivanja.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (odabirIstrazivanja.selectedItem.toString() != null) {
                        odabirGrupa.isEnabled = true
                        popuniSpinnerGrupa()

                    } else {
                        odabirGrupa.isEnabled = false
                        dodajIstrazivanjeDugme.isEnabled = false
                    }
                  /*  dodajIstrazivanjeDugme.isEnabled =
                        odabirGodina.selectedItem.toString() != "" && odabirIstrazivanja.selectedItem.toString() != "" && odabirGrupa.selectedItem.toString() != ""

                   */
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    popuniSpinnerGrupa()
                    odabirGrupa.isEnabled = false
                   /* dodajIstrazivanjeDugme.isEnabled =
                        odabirGodina.selectedItem.toString() != "" && odabirIstrazivanja.selectedItem.toString() != "" && odabirGrupa.selectedItem.toString() != ""
                    */
                }
            }



       /* if (odabirGrupa.selectedItem!=null) {
            nadjiGrupu()
            dodajIstrazivanjeDugme.setOnClickListener { trenutnaGrupa?.id?.let { it1 -> upisiMe(it1) } }
        }
        else dodajIstrazivanjeDugme.isEnabled=false

        */
        if (!AnketaRepository.isOnline(AnketaRepository.getContext())){
            dodajIstrazivanjeDugme.isEnabled=false
        }
        dodajIstrazivanjeDugme.setOnClickListener { upisiMe() }
        return view1
    }

   fun nadjiGrupu():Grupa?{
       GlobalScope.launch {

           var sveGrupe=IstrazivanjeIGrupaRepository.getGrupe()

           delay(200)
           for (i in sveGrupe!!){
               if (i.naziv==odabirGrupa.selectedItem.toString()){
                   trenutnaGrupa=i
                   break
               }
           } }
        return trenutnaGrupa
    }

    fun spinnerIstrazivanjeSuccess(istrazivanja:List<Istrazivanje>){
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                var mojaIstrazivanja=IstrazivanjeIGrupaRepository.getMojaIstrazivanja()
                var konacnaLista: MutableList<Istrazivanje> = mutableListOf()
                for (i in istrazivanja){
                    if (mojaIstrazivanja != null) {
                        if (!mojaIstrazivanja.contains(i)) konacnaLista.add(i)

                    }
                }
                if(konacnaLista.size==0) konacnaLista= istrazivanja as MutableList<Istrazivanje>
                var adapterIstrazivanja2 = ArrayAdapter(view1.context, android.R.layout.simple_list_item_1, konacnaLista)
                odabirIstrazivanja.adapter=adapterIstrazivanja2
            }
        }
    }
    private fun popuniSpinnerIstrazivanja() {
        var godina=odabirGodina.selectedItem.toString()
        istrazivanjaVM.getIstrazivanjeByGodina(godina.toInt(),::spinnerIstrazivanjeSuccess, ::onError)
    }

    private fun popuniSpinnerGrupa(){
         istrazivanjaVM.getAll(onSuccess = ::svaIstrazivanjaSuccess,  onError= ::onError)
        var trazeniId:Int=-1
        if (svaIstrazivanja.isNotEmpty()){
            var trazeniString=odabirIstrazivanja.selectedItem.toString()
            for ( i in svaIstrazivanja){
                if (i.naziv==trazeniString) trazeniId=i.id
            }
        }
        if(trazeniId!=-1) grupeVM.getGroupsByIstrazivanje(trazeniId,::spinnerGrupeSuccess,::onError)
       else onError()
    }
    fun svaIstrazivanjaSuccess(x:List<Istrazivanje>){
        svaIstrazivanja=x
    }
    fun spinnerGrupeSuccess(x:List<Grupa>){
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                var adapterGrupa2 = ArrayAdapter(
                    view1.context,
                    android.R.layout.simple_list_item_1,
                   x
                )
                odabirGrupa.adapter = adapterGrupa2
            }
        }
    }
    /*fun dajIstrazivanje(){

        GlobalScope.launch {
            var svaIstrazivanja=IstrazivanjeIGrupaRepository.getSvaIstrazivanja()
            if (svaIstrazivanja != null) {
                for (i in svaIstrazivanja){
                    if (i.naziv==odabirIstrazivanja.selectedItem.toString()) {
                        trenutnoIstrazivanje=i
                        break
                    }
                }
            }
        }
    }

     */

    fun upisiMe(){

      istrazivanjaVM.upisiUGrupu((odabirGrupa.selectedItem as Grupa).id )
        MainActivity.vpAdapter.refreshFragment(1,FragmentPoruka.newInstance("Uspješno ste upisani u grupu: "+odabirGrupa.selectedItem.toString()+" istraživanja: "+ odabirIstrazivanja.selectedItem.toString()+"!"))
    }

    fun onError(){
        Log.v("Lokacija: FragmentIstrazivanje","Greska u spinneru")
    }

}

