package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.R

class FragmentPoruka(tekstStr:String): Fragment() {
    private lateinit var tvPoruka:TextView
    private var tekst=tekstStr
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_poruka,container,false)
        tvPoruka=view.findViewById(R.id.tvPoruka)
        tvPoruka.text=tekst
        return view
    }
}