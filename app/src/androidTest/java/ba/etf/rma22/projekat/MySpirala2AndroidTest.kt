package ba.etf.rma22.projekat

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as Is

@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {
    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testZaZadatak1 (){
        val mojeAnkete=AnketaRepository.getMyAnkete()
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.filterAnketa)).perform(click())
        Espresso.onData(CoreMatchers.allOf(Is(CoreMatchers.instanceOf(String::class.java)),Is("Sve moje ankete"))).perform(click())
        onView(withId(R.id.listaAnketa)).check(UtilTestClass.hasItemCount(mojeAnkete.size))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        onView(withId(R.id.odabirGodina)).perform(click())
        Espresso.onData(CoreMatchers.allOf(Is(CoreMatchers.instanceOf(String::class.java)), Is("4"))).perform(click())
        onView(withText("Uspješno ste upisani u grupu: Grupa3 istraživanja: PFSA istraživanje!"))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        Thread.sleep(1000)
        onView(withId(R.id.filterAnketa)).perform(click())
        Espresso.onData(CoreMatchers.allOf(Is(CoreMatchers.instanceOf(String::class.java)),Is("Sve moje ankete"))).perform(click())
        val mojeAnketePoslijeUpisa=AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).check(UtilTestClass.hasItemCount(mojeAnketePoslijeUpisa.size))
    }
}