package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.R

class FragmentPoruka: Fragment() {
    companion object{
        fun newInstance(tekstStr:String):FragmentPoruka=FragmentPoruka().apply {
            arguments=Bundle().apply {
                putString("tekstStr",tekstStr)
            }
        }
    }
    private lateinit var tvPoruka:TextView
    private lateinit var tekst:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.getString("tekstStr")?.let { tekst=it }
        val view=inflater.inflate(R.layout.fragment_poruka,container,false)
        tvPoruka=view.findViewById(R.id.tvPoruka)
        tvPoruka.text=tekst
        return view
    }
}