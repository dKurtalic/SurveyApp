package ba.etf.rma22.projekat.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*
import kotlin.math.roundToInt

class AnketaAdapter(
    private var anketaArray:List<Anketa>,
    private val onItemClick: ((anketa:Anketa)->Unit)?):
    RecyclerView.Adapter<AnketaAdapter.AnketaViewHolder>()  {

    inner class AnketaViewHolder(pogled: View): RecyclerView.ViewHolder(pogled){
        val nazivAnkete=pogled.findViewById<TextView>(R.id.nazivAnkete)
        val opisTekst=pogled.findViewById<TextView>(R.id.opisTekst)
        val progres=pogled.findViewById<ProgressBar>(R.id.progressBar)
        val datumTekst=pogled.findViewById<TextView>(R.id.datumText)
        val imageView=pogled.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder (vg: ViewGroup, viewType: Int): AnketaViewHolder {
        val pogled=LayoutInflater.from(vg.context).inflate(R.layout.anketa_layout,vg,false)
        return AnketaViewHolder(pogled)
    }

    override fun onBindViewHolder(holder: AnketaViewHolder, position: Int) {
            holder.nazivAnkete.text=anketaArray[position].naziv
          if (anketaArray[position].nazivIstrazivanja==null) holder.opisTekst.text="Nepoznato istrazivanje"
            else holder.opisTekst.text=anketaArray[position].nazivIstrazivanja
            val statusAnkete:String= dajStatusAnkete(position)

            val context : Context=holder.datumTekst.context
            val id=context.resources.getIdentifier(statusAnkete,"drawable",context.packageName)
            holder.imageView.setImageResource(id)

            var pom=dajProgres(anketaArray[position].progres)
            holder.progres.progress= pom
           postaviIzgledAnkete(statusAnkete,holder,position)

        holder.itemView.setOnClickListener { onItemClick!!(anketaArray[position]) }
    }
    private fun postaviIzgledAnkete(statusAnkete: String, holder: AnketaViewHolder,position:Int){

        if (statusAnkete.equals("plava")) {
            holder.datumTekst.text="Anketa urađena: "+ dajDatum(anketaArray[position].datumRada)
        }
        else if (statusAnkete.equals("zuta")) {
            holder.datumTekst.text="Vrijeme aktiviranja: "+dajDatum(anketaArray[position].datumPocetak)
        }
        else if (statusAnkete.equals("zelena")){
            if (anketaArray[position].datumKraj==null) holder.datumTekst.text="Nepoznato"
            else holder.datumTekst.text="Vrijeme zatvaranja: "+dajDatum(anketaArray[position].datumKraj)
        }
        else holder.datumTekst.text="Anketa zatvorena: "+dajDatum(anketaArray[position].datumKraj)
    }

    private fun dajProgres (progres: Float):Int{
        var p= progres * 100
        if ((p%2).roundToInt()!=0) {
            p+=10
        }
        return p.toInt()
    }



    private fun dajDatum(datum:Date?):String{
        var dan=datum!!.date
        var mjesec=datum!!.month+1
        var godina=datum!!.year
        godina+=1900
        var danString:String
        var mjesecString: String
        if (dan<10) danString = "0$dan"
            else danString=dan.toString()
        if (mjesec<10) mjesecString= "0$mjesec"
            else mjesecString=mjesec.toString()
        return "$danString.$mjesecString.$godina"
    }


    private fun dajStatusAnkete(position : Int):String {

               // var pokusaj = TakeAnketaRepository.getPocetuAnketu(anketaArray[position].id)

                // if (pocetniStatus!="") return pocetniStatus
                val kalendar: Calendar = Calendar.getInstance()
                kalendar.set(LocalDateTime.now().year, LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
                val danasnjiDatum: Date = kalendar.time
                if (anketaArray[position].datumRada != null) return "plava"
                else if (anketaArray[position].datumPocetak!!.after(danasnjiDatum)) return "zuta"
                else if (anketaArray[position].datumPocetak!!.before(danasnjiDatum) && anketaArray[position].datumKraj != null && anketaArray[position].datumKraj?.after(danasnjiDatum) == true) return "zelena"
                else if (anketaArray[position].datumPocetak!!.before(danasnjiDatum) && anketaArray[position].datumKraj != null && anketaArray[position].datumKraj?.before(danasnjiDatum) == true && anketaArray[position].datumRada == null) return "crvena"
                else return "zelena"


        var pocetniStatus=anketaArray[position].dajStatusAnkete()
        Log.v("AnketaAdapter","pocetniStatus: $pocetniStatus")


    }

    override fun getItemCount(): Int {
        return anketaArray.count()
    }
    fun updateAnkete(listaAnketa:List<Anketa>):Unit{
        this.anketaArray=listaAnketa
        notifyDataSetChanged()
    }

}


