package bruno.cci.testpreparation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import bruno.cci.testpreparation.R
import bruno.cci.testpreparation.models.Destination
import bruno.cci.testpreparation.models.Trip
import bruno.cci.testpreparation.ui.fragments.HomeFragment
import bruno.cci.testpreparation.ui.fragments.RefreshableFragment
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * On donne un context à la base de donnée Realm pour qu'elle s'initialise.
         */
        Realm.init(this)

        /**
         * S'il n'y a pas de Trip dans la base de données on y insère nos données de test.
         */

        Trip().queryFirst().let {
            if (it == null) {
                listOf(
                    Trip().apply {
                        title = "Test 1"
                        dateStart = System.currentTimeMillis() + 100000
                        dateEnd = System.currentTimeMillis() + 500000
                        destination = Destination().apply {
                            name = "Colmar"
                            latitude = 48.079357
                            longitude = 7.358512
                        }
                    },
                    Trip().apply {
                        title = "Test 2"
                        dateStart = System.currentTimeMillis() + 200000
                        dateEnd = System.currentTimeMillis() + 800000
                        destination = Destination().apply {
                            name = "Strasbourg"
                            latitude = 48.583478
                            longitude = 7.753927
                        }
                    },
                    Trip().apply {
                        title = "Test 3"
                        dateStart = System.currentTimeMillis() + 600000
                        dateEnd = System.currentTimeMillis() + 1000000
                        destination = Destination().apply {
                            name = "Mulhouse"
                            latitude = 47.761080
                            longitude = 7.333606
                        }
                    }
                ).forEach {
                    it.save()
                }
            }
        }

        val navController = findNavController(R.id._navHostFragment)

        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(
                setOf(
                    R.id.navigation_home,
                    R.id.navigation_dashboard,
                    R.id.navigation_notifications
                )
            )
        )
        _navView.setupWithNavController(navController)

    }

    /**
     * On récupère l'évenement de "fin d'activité" de la TripActivity de cette manière.
     * La TripActivity est lancée avec un "startActivityForResult" et le requestCode TripActivity.TRIP_REQUEST_CODE.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TripActivity.TRIP_REQUEST_CODE) {
            refreshHomeFragment()
        }
    }

    /**
     * Si on arrive a récupérer l'instance du fragment avec la liste, on lance la méthode refreshList présente dans l'interface RefreshableFragment. HomeFragment et DashboardFragment implémente la méthode.
     * Sinon, le fragment n'est probablement pas affiché et la liste sera donc créer si on y navigue, avec les dernières informations.
     */
    private fun refreshHomeFragment() {
        getForegroundFragmentIfRefreshable()?.refreshList()
    }

    /**
     * On récupère les fragments dans le NavHostFragment, il n'y a que le fragment actuellement sélectionnée dans la BottomNavigationBar la plupart du temps.
     * Si ce n'est pas un RefreshableFragment on renvois simplement null.
     */
    private fun getForegroundFragmentIfRefreshable(): RefreshableFragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id._navHostFragment)
        return navHostFragment?.childFragmentManager?.fragments?.firstOrNull().let {
            if (it is RefreshableFragment) it else null
        }
    }
}
