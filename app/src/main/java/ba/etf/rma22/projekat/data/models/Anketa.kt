package ba.etf.rma22.projekat.data.models

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*

@Entity
data class Anketa(
    @PrimaryKey @SerializedName("id") var id: Int,
    @ColumnInfo (name = "naziv") @SerializedName("naziv") var naziv: String,
    var nazivIstrazivanja: String?,
    @ColumnInfo (name= "datumPocetak") @SerializedName("datumPocetak") var datumPocetak: Date?,
    @ColumnInfo (name= "datumKraj") @SerializedName("datumKraj") var datumKraj: Date?,
    var datumRada: Date?,
    @ColumnInfo (name= "trajanje") @SerializedName("trajanje") var trajanje: Int,
    var nazivGrupe: String?,
    var progres: Float=0.0f,
    var upisana: Int
    )
{

    private var status:String= ""
    init {
        dajStatusAnkete()
    }
    fun getStatus():String {return status}
    fun setStatus(s:String){status=s}
    override fun equals(other: Any?): Boolean {
        return other is Anketa && this.naziv == other.naziv && this.nazivIstrazivanja == other.nazivIstrazivanja
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
     fun izracunajStatusAnkete():String{
        GlobalScope.launch {
             val kalendar: Calendar= Calendar.getInstance()
             kalendar.set(LocalDateTime.now().year, LocalDateTime.now().monthValue, LocalDateTime.now().dayOfMonth)
             val danasnjiDatum:Date=kalendar.time
             var pokusaj=TakeAnketaRepository.getPocetuAnketu(id)
            if (pokusaj!=null) Log.v("Anketa","Pokusaj : "+pokusaj.id) else Log.v("Anketa","Pokusaj : null")
             if (pokusaj != null) {
                 if (pokusaj.datumRada!=null) status="plava" //ovdje pokusaj.datumRada
                 else if (datumPocetak!=null && datumPocetak!!.after(danasnjiDatum)) status="zuta"
                 else if(datumPocetak!=null && datumPocetak!!.before(danasnjiDatum) && datumKraj!=null && datumKraj!!.after(danasnjiDatum))  status="zelena"
                 else if (datumPocetak!=null && datumPocetak!!.before(danasnjiDatum) && datumKraj!=null && datumKraj!!.before(danasnjiDatum) && datumRada==null)
                     status="crvena"
        }
        }
            return status
        }
    fun dajStatusAnkete():String {
        if (status=="") status=izracunajStatusAnkete()
        Log.v("Anketa ","Status je $status")
        return status
    }

    fun promijeniStatusAnkete(noviStatus:String){
        status=noviStatus
    }
}