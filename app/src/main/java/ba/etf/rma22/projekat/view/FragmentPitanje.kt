package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.AppDatabase
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import ba.etf.rma22.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt


class FragmentPitanje: Fragment() {
    private lateinit var anketa:Anketa
    private lateinit var pitanje:Pitanje
    private var redniBroj=0
    private lateinit var listaSvihAnketa:List<Anketa>
    private lateinit var pokusaj:AnketaTaken
    companion object{
      fun newInstance(pitanje:Pitanje,anketa:Anketa, brojac:Int, lista:List<Anketa>, pokusaj:AnketaTaken):
              FragmentPitanje=FragmentPitanje().apply {
                this.anketa=anketa
                this.pitanje=pitanje
                this.redniBroj=brojac
                this.listaSvihAnketa=lista.toMutableList()
                this.pokusaj=pokusaj
              }
            }

    private lateinit var tekst:String
    private lateinit var odgovori:List<String>
    private lateinit var tekstPitanja: TextView
    private lateinit var odgovoriLista: ListView
    private lateinit var dugmeZaustavi: Button
    private var odabraniOdgovor: Int?=-1
    private lateinit var view1:View
    private var pitanjeAnketaVM=PitanjeAnketaViewModel()
    private var odgVM= OdgovorViewModel()
    private lateinit var listAdapter: ListElementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tekst=pitanje.tekstPitanja
        odgovori=pitanje.opcije
        MainActivity.viewPager.offscreenPageLimit=2
        view1=inflater.inflate(R.layout.fragment_pitanje,container, false)
        tekstPitanja=view1.findViewById(R.id.tekstPitanja)
        odgovoriLista=view1.findViewById(R.id.odgovoriLista)
        dugmeZaustavi=view1.findViewById(R.id.dugmeZaustavi)

        tekstPitanja.text=tekst

        listAdapter=ListElementAdapter(view1.context,R.layout.custom_cell,odgovori,anketa,redniBroj,pitanje,pokusaj)

        odgovoriLista.adapter=listAdapter

        odgovoriLista.setOnItemClickListener { parent, view, position, id ->
            if (anketa.dajStatusAnkete()!="crvena" && anketa.dajStatusAnkete()!="plava"){
            var odg=view.findViewById<TextView>(R.id.answ)

                GlobalScope.launch {
               //    if (!odgRepo.getOdgovoriAnketa(anketa.id)!!.contains(PitanjeAnketa(pitanje.naziv,anketa.naziv,anketa.nazivIstrazivanja))){
                    listAdapter.obojiOdgovor(odg)
                  //  var pitanjee= PitanjeAnketa(pitanje.naziv, anketa.naziv, anketa.nazivIstrazivanja)
                  //  pitanjee.postaviOdgovor(odg.text.toString())
                   // odgRepo.postaviOdgovorAnketa(anketa.id,pitanje.id,position)
                    Log.v("FragmentPitanje", "idpokusaj ${pokusaj.id}")
                    odgVM.postaviOdgovorAnketa(anketa.id,pokusaj,pitanje.id,position)



                }
            //}
            }
        }
        dugmeZaustavi.setOnClickListener { prekiniAnketu() }
        return view1
        }

    private fun postavljanjOdgovoraSuccess(position:Int,view:View){
        GlobalScope.launch {
            var trazeniOdgovor=PitanjeAnketaRepository.dajOdgovorZaPitanje(pitanje,anketa,pokusaj)
            if (odgovoriLista.selectedItemPosition==position){
                listAdapter.obojiOdgovor(view)
            }
        }

    }
    private fun onError(){
        Log.v("Lokacija: FragmentPitanje", "Doslo je do greske pri dobavljanju odgovora sa servera")
    }

    private fun prekiniAnketu(){
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                MainActivity.vpAdapter.removeAll()

                MainActivity.vpAdapter.add(0, FragmentAnkete.newInstance())

                MainActivity.vpAdapter.add(1, FragmentIstrazivanje.newInstance())

                MainActivity.viewPager.currentItem = 0

                var odgovori=TakeAnketaRepository.getMojiOdgovori(pokusaj)
                if (odgovori==null) odgovori= emptyList()
                var progres: Float = izracunajProges(
                    odgovori.size.toDouble(),
                    PitanjeAnketaRepository.dajBrojPitanja(anketa.id)
                ).toFloat()
                progres /= 100F

                /* val indeks= listOfAllSurveys.indexOf(listaSvihAnketa.find { ank->ank==anketa })
        promijeniPodatke(indeks,Anketa(anketa.naziv,anketa.nazivIstrazivanja,anketa.datumPocetak,anketa.datumKraj, anketa.datumRada,anketa.trajanje,anketa.nazivGrupe,progres))
        FragmentAnkete.anketaAdapter.updateAnkete(listOfAllSurveys)

        */
            }
        }
    }

    private fun izracunajProges(brojOdgovora: Double, brojPitanja: Int): Int{
        var broj:Double=(brojOdgovora/brojPitanja)*100.0
        var vrati=broj.toInt()
        var novi=vrati/20.0
        return (novi.roundToInt()*20)
    }

}