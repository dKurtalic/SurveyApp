package ba.etf.rma22.projekat.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import java.time.LocalDateTime
import java.util.*
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.days

class AnketaAdapter(
    private var anketaArray:List<Anketa>,
    private val onItemClick: (anketa:Anketa)->Unit):
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
            holder.opisTekst.text=anketaArray[position].nazivIstrazivanja
            val statusAnkete:String= dajStatusAnkete(position)
            val context : Context=holder.datumTekst.context
            val id=context.resources.getIdentifier(statusAnkete,"drawable",context.packageName)
            holder.imageView.setImageResource(id)
            var pom=dajProgres(anketaArray[position].progres)
          //  if (((pom*10).toInt()%2!=0)) pom+=0.6F
            holder.progres.progress= pom


            if (statusAnkete.equals("plava")) holder.datumTekst.text="Anketa urađena: "+ dajDatum(anketaArray[position].datumRada)
            else if (statusAnkete.equals("zuta")) holder.datumTekst.text="Vrijeme aktiviranja: "+dajDatum(anketaArray[position].datumPocetak)
            else if (statusAnkete.equals("zelena")) holder.datumTekst.text="Vrijeme zatvaranja: "+dajDatum(anketaArray[position].datumKraj)
            else holder.datumTekst.text="Anketa zatvorena: "+dajDatum(anketaArray[position].datumKraj)

        holder.itemView.setOnClickListener { onItemClick(anketaArray[position]) }
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
        var mjesec=datum!!.month
        var godina=datum!!.year
        godina+=1900
        var danString:String
        var mjesecString: String
        if (dan<10) danString ="0"+dan
            else danString=dan.toString()
        if (mjesec<10) mjesecString="0"+mjesec
            else mjesecString=mjesec.toString()
        return "$danString.$mjesecString.$godina"
    }

    private fun dajStatusAnkete(position : Int):String {
        val kalendar: Calendar= Calendar.getInstance()
        kalendar.set(LocalDateTime.now().year,LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
        val danasnjiDatum:Date=kalendar.time
        if (anketaArray[position].datumRada!=null) return "plava"
        else if (anketaArray[position].datumPocetak.after(danasnjiDatum)) return "zuta"
        else if(anketaArray[position].datumPocetak.before(danasnjiDatum) &&
            anketaArray[position].datumKraj.after(danasnjiDatum)) return "zelena"
        else if (anketaArray[position].datumPocetak.before(danasnjiDatum) &&
            anketaArray[position].datumKraj.before(danasnjiDatum) && anketaArray[position].datumRada==null) return "crvena"
        return "zelena"
    }

    override fun getItemCount(): Int {
        return anketaArray.count()
    }
    fun updateAnkete(listaAnketa: List<Anketa>){
        this.anketaArray=listaAnketa
        notifyDataSetChanged()
    }
    fun getAnketaArray():List<Anketa>{return anketaArray}

}


