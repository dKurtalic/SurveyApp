package ba.etf.rma22.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PitanjeTest {
    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun ucitajuSePitanja() {
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.filterAnketa)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Sve moje ankete"))).perform(click())
        val ankete = AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(hasDescendant(withText(ankete[0].naziv)),
            hasDescendant(withText(ankete[0].nazivIstrazivanja))), click()))
        val pitanja = PitanjeAnketaRepository.getPitanja(ankete[0].naziv, ankete[0].nazivIstrazivanja)
        for ((indeks,pitanje) in pitanja.withIndex()) {
            onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(indeks))
            onView(allOf(isDisplayed(),withId(R.id.tekstPitanja))).check(matches(withText(pitanja[indeks].tekst)))
        }
    }

    @Test
    fun testPredajAnketu() {
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.filterAnketa)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Sve moje ankete"))).perform(click())
        val ankete = AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(hasDescendant(withText(ankete[0].naziv)),
            hasDescendant(withText(ankete[0].nazivIstrazivanja))), click()))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        onView(withId(R.id.dugmePredaj)).perform(click())
        onView(withSubstring("Završili ste anketu")).check(matches(isDisplayed()))
    }

}