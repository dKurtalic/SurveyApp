package ba.etf.rma22.projekat.data.models

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PitanjeAnketa(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") var id:Int,
    @ColumnInfo(name="naziv")var naziv:String, //naziv pitanja
    @ColumnInfo(name="anketa")var anketa:String, // naziv ankete
    @ColumnInfo(name="nazivIstrazivanja")var nazivIstrazivanja:String?
) {

    private var odgovoreno=false
    private var odgovor:String =""

    fun setOdgovoreno(odgovoreno:Boolean){this.odgovoreno=odgovoreno}
    fun getOdgovoreno():Boolean{return this.odgovoreno}
    fun setOdgovor(odgovor:String){this.odgovor=odgovor}
    fun getOdgovor():String{return this.odgovor}

    fun postaviOdgovor(odg:String){
      if (!odgovoreno)  {
          odgovor=odg
          odgovoreno=true
      }
        Log.v("PitanjeAnketa", "odgovoreno: $odgovoreno, odgovor: $odgovor za pitanje: $naziv")
    }

    fun dajOdgovor():String{
        return odgovor
    }

    override fun equals(other: Any?): Boolean {
        if (other is PitanjeAnketa) {
            return (naziv==other.naziv && anketa==other.anketa && nazivIstrazivanja==other.nazivIstrazivanja)
        }
        return false
    }

}
