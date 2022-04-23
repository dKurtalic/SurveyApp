package ba.etf.rma22.projekat

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.UtilTestClass.Companion.hasItemCount
import ba.etf.rma22.projekat.UtilTestClass.Companion.itemTest
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as Is


@RunWith(AndroidJUnit4::class)
class PocetniTest {

    @get:Rule
    val intentsTestRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun postojiSveNaPocetnoj() {

        onView(withId(R.id.filterAnketa)).check(matches(isDisplayed()))
        onView(withId(R.id.listaAnketa)).check(matches(isDisplayed()))
        onView(withId(R.id.pager)).check(matches(isDisplayed()))

        var listaOdabira = listOf<String>("Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete")

        for (odabir in listaOdabira) {
            onView(withId(R.id.filterAnketa)).perform(click())
            onData(allOf(Is(instanceOf(String::class.java)), Is(odabir))).perform(click())
        }

    }

    @Test
    fun popuniAnketeGetDone() {
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(0))
        onView(withId(R.id.filterAnketa)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), Is("Sve moje ankete"))).perform(click())
        val ankete = AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).check(hasItemCount(ankete.size))
        for (anketa in ankete) {
            itemTest(R.id.listaAnketa, anketa)
        }

    }

    @Test
    fun godineTest() {
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(1))
        var listaOdabira = listOf<String>("1", "2", "3", "4", "5")
        for (odabir in listaOdabira) {
            onView(withId(R.id.odabirGodina)).perform(click())
            onData(allOf(Is(instanceOf(String::class.java)), Is(odabir))).perform(click())
        }
    }

}