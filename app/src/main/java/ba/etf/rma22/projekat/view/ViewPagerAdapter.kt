package ba.etf.rma22.projekat.view


import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewPagerAdapter(
    private val fragments:MutableList<Fragment>,
    activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return fragments.size
    }
    fun getItem(index:Int): Fragment {
        return fragments[index]
    }
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
    fun refreshFragment(index: Int, fragment: Fragment) {

                fragments[index] = fragment
                notifyItemChanged(index)

    }
    fun add(index: Int, fragment: Fragment) {

                fragments.add(index, fragment)
                notifyItemChanged(index)

    }
    fun addAll(list:List<Fragment>) {
        var brojac=0
        for (i in list) {
            fragments.add(i)
            notifyItemChanged(brojac)
            brojac++
        }

    }
    fun remove(index: Int) {

                fragments.removeAt(index)
                notifyItemChanged(index)

    }
    fun removeAll(){

                var size: Int = itemCount
                size -= 1

                for (i in size downTo 0) {
                    remove(i)
                    notifyItemChanged(i)
                }

                notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.find { it.hashCode().toLong() == itemId } != null
    }

}