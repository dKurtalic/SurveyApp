package ba.etf.rma22.projekat

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs
import org.hamcrest.CoreMatchers.`is` as Is

@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {
    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testZaZadatak1 (){
        val anketaVM=AnketaViewModel()
        val mojeAnkete=anketaVM.getMyAnkete()

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.filterAnketa)).perform(click())
        onData(CoreMatchers.allOf(Is(CoreMatchers.instanceOf(String::class.java)),Is("Sve moje ankete"))).perform(click())
        onView(withId(R.id.listaAnketa)).check(UtilTestClass.hasItemCount(mojeAnkete.size))

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        onView(withId(R.id.odabirGodina)).perform(click())
        onData(CoreMatchers.allOf(Is(CoreMatchers.instanceOf(String::class.java)), Is("4"))).perform(click())
        onView(withId(R.id.dodajIstrazivanjeDugme)).perform(click())
        onView(withText("Uspješno ste upisani u grupu: Grupa3 istraživanja: PFSA istraživanje!"))

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.filterAnketa)).perform(click())
        onData(CoreMatchers.allOf(Is(CoreMatchers.instanceOf(String::class.java)),Is("Sve moje ankete"))).perform(click())
        val mojeAnketePoslijeUpisa=anketaVM.getMyAnkete()
        onView(withText("PFSA istraživanje"))
        onView(withId(R.id.listaAnketa)).check(UtilTestClass.hasItemCount(mojeAnketePoslijeUpisa.size))
    }
    @Test
    fun testZaZadatak2 (){
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        val mojeAnkete = AnketaRepository.getMyAnkete()

        //pošto postoje ankete na koje je korisnik upisan po default-u, iskoristit ću njih

        //otvaram anketu
        onView(withId(R.id.listaAnketa)).perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
                (CoreMatchers.allOf(hasDescendant(withText(mojeAnkete[0].naziv)),
                hasDescendant(withText(mojeAnkete[0].nazivIstrazivanja))), click()))

                val pitanjaZaAnketu : List<Pitanje> = PitanjeAnketaRepository.getPitanja(mojeAnkete[0].naziv,mojeAnkete[0].nazivIstrazivanja)

                //odgovaram na prvo pitanje
                var indeks=0
                onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(indeks))
                onView(AllOf.allOf(isDisplayed(), withId(R.id.tekstPitanja))).check(matches(withText(pitanjaZaAnketu[indeks].tekst)))
                onData(anything()).inAdapterView(AllOf.allOf(isDisplayed(),withId(R.id.odgovoriLista))).atPosition(0).perform(click())

                //testiram dugme zatvori
                onView(AllOf.allOf(isDisplayed(),withId(R.id.dugmeZaustavi))).perform(click())

                //opet otvaram anketu
                onView(withId(R.id.listaAnketa)).perform(
                    RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>
                    (CoreMatchers.allOf(hasDescendant(withText(mojeAnkete[0].naziv)),
                    hasDescendant(withText(mojeAnkete[0].nazivIstrazivanja))), click()))

              indeks+=1
              onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(indeks))
              onData(anything()).inAdapterView(AllOf.allOf(isDisplayed(),withId(R.id.odgovoriLista))).atPosition(1).perform(click())

              indeks+=2
              onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(indeks))

              //predajemAnketu
              onView(withText("60%"))
              onView(withId(R.id.dugmePredaj)).perform(click())

              onView(withId(R.id.listaAnketa)).check(matches(hasDescendant(withText(mojeAnkete[0].naziv)))).check(matches(hasDescendant(withImage(R.drawable.plava))))
        }

    }



  fun withImage(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("Drawable does not contain image with id: $id")
    }

    override fun matchesSafely(item: View): Boolean {
        val context: Context = item.context
        var bitmap: Bitmap? = context.getDrawable(id)?.toBitmap()
        if (item !is ImageView) return false
        val origBitmap = item.drawable.toBitmap()
        bitmap = bitmap!!.scale(origBitmap.width, origBitmap.height)
        return BitmapUtil.razlikaBitmapa(origBitmap, bitmap) < 0.01
    }
}

class BitmapUtil {
    companion object {
        private fun pixelDiff(rgb1: Int, rgb2: Int): Int {
            val r1 = rgb1 shr 16 and 0xff
            val r2 = rgb2 shr 16 and 0xff
            return abs(r1 - r2)
        }

        fun razlikaBitmapa(b1: Bitmap, b2: Bitmap): Float {
            val pikseli1 = IntArray(b1.width * b1.height)
            val pikseli2 = IntArray(b2.width * b2.height)
            b1.getPixels(pikseli1, 0, b1.width, 0, 0, b1.width, b1.height)
            b2.getPixels(pikseli2, 0, b2.width, 0, 0, b2.width, b2.height)
            if (pikseli1.size != pikseli2.size) return 1.0f
            var razlika: Long = 0
            for (i in pikseli1.indices) {
                razlika += pixelDiff(pikseli1[i], pikseli2[i]).toLong()
            }
            return razlika.toFloat() / (255 * pikseli1.size).toFloat()
        }
    }
}

