package ba.etf.rma22.projekat

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository

class UpisIstrazivanje:AppCompatActivity() {
    private lateinit var odabirGodina: Spinner
    private lateinit var odabirGrupa: Spinner
    private lateinit var odabirIstrazivanja: Spinner
    private lateinit var istrazivanja: IstrazivanjeRepository
    private lateinit var grupe: GrupaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upis_istrazivanje_layout)

        odabirGodina=findViewById(R.id.odabirGodina)
        var opcijeGodine:List<String> = listOf("1","2","3","4","5")
        val adapterGodine = ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, opcijeGodine)
        odabirGodina.adapter=adapterGodine

        odabirGrupa=findViewById(R.id.odabirGrupa)
        grupe= GrupaRepository()
        val adapterGrupe = ArrayAdapter (this, android.R.layout.simple_list_item_1, grupe.getAllGroups())
        odabirGrupa.adapter=adapterGrupe

        odabirIstrazivanja=findViewById(R.id.odabirIstrazivanja)
        istrazivanja= IstrazivanjeRepository()
        val adapterIstrazivanja = ArrayAdapter (this, android.R.layout.simple_list_item_1, istrazivanja.getAll())
        odabirIstrazivanja.adapter=adapterIstrazivanja

    }
}