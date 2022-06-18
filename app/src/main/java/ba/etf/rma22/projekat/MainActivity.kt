package ba.etf.rma22.projekat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.repositories.*
import ba.etf.rma22.projekat.view.FragmentAnkete
import ba.etf.rma22.projekat.view.FragmentIstrazivanje
import ba.etf.rma22.projekat.view.FragmentPoruka
import ba.etf.rma22.projekat.view.ViewPagerAdapter
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
   companion object{
       lateinit var viewPager:ViewPager2
       lateinit var vpAdapter:ViewPagerAdapter
       lateinit var fragments:ArrayList<Fragment>
        var connection : Boolean = true
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager=findViewById(R.id.pager)
        fragments= arrayListOf(FragmentAnkete(),FragmentIstrazivanje())
        vpAdapter= ViewPagerAdapter(fragments,this)
        viewPager.adapter=vpAdapter

        AnketaRepository.setContext(this)
        AccountRepository.setContext(this)
        IstrazivanjeIGrupaRepository.setContext(this)
        OdgovorRepository.setContext(this)
        PitanjeAnketaRepository.setContext(this)
        TakeAnketaRepository.setContext(this)

        connection = AnketaRepository.isOnline(this)


        viewPager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
           override fun onPageSelected(position:Int){
              if (viewPager.currentItem==0 && vpAdapter.itemCount>=2 && vpAdapter.getItem(1) is FragmentPoruka){
                  vpAdapter.refreshFragment(1,FragmentIstrazivanje())

              }
               super.onPageSelected(position)
                }
            }
        )

        }
    }
