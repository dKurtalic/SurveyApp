package ba.etf.rma22.projekat.view

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.R

class ViewPagerAdapter(
    private val fragments:MutableList<Fragment>,
    activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return fragments.size
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
    fun remove(index: Int) {
        fragments.removeAt(index)
        notifyItemChanged(index)
    }
    fun removeAll(){

        var size:Int= itemCount
        size-=1
        Log.v("tag", "Size: $size")
        for (i in size downTo 0) remove(i)
    }
    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.find { it.hashCode().toLong() == itemId } != null
    }

}