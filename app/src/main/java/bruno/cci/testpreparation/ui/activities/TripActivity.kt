package bruno.cci.testpreparation.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bruno.cci.testpreparation.R
import bruno.cci.testpreparation.ui.models.Trip
import kotlinx.android.synthetic.main.activity_trip.*
import java.text.SimpleDateFormat
import java.util.*

class TripActivity : AppCompatActivity() {

    /**
     * Le companion object défini les méthodes statiques (qui peuvent être appelées sans avoir d'instance de la classe)
     */
    companion object {
        private const val EXTRA_BUNDLE = "EXTRA_BUNDLE"
        private const val EXTRA_TRIP_TITLE = "EXTRA_TRIP_TITLE"
        private const val EXTRA_TRIP_DATE_START = "EXTRA_TRIP_DATE_START"
        private const val EXTRA_TRIP_DATE_END = "EXTRA_TRIP_DATE_END"
        private const val EXTRA_TRIP_DESTINATION = "EXTRA_TRIP_DESTINATION"

        /**
         * Méthode statique ouvrir cette activité et afficher le détail d'un Trip
         * Les champs du modèle Trip sont tous passés séparémment. L'autre solution est de rendre la classe Parcelable.
         */
        fun start(context: Context, trip: Trip) {
            context.startActivity(
                Intent(context, TripActivity::class.java).apply {
                    putExtra(
                        EXTRA_BUNDLE,
                        Bundle().apply {
                            putString(EXTRA_TRIP_TITLE, trip.title)
                            putLong(EXTRA_TRIP_DATE_START, trip.dateStart)
                            putLong(EXTRA_TRIP_DATE_END, trip.dateEnd)
                            putString(EXTRA_TRIP_DESTINATION, trip.destination)
                        }
                    )
                }
            )
        }
    }

    /**
     * Méthode simple pour récupérer dans une variable un Trip recréer à partir des éléments passé en Extra.
     */
    private val tripFromExtra by lazy {
        intent.getBundleExtra(EXTRA_BUNDLE).let {bundle ->
            Trip(
                bundle.getString(EXTRA_TRIP_TITLE, ""),
                bundle.getLong(EXTRA_TRIP_DATE_START, 0L),
                bundle.getLong(EXTRA_TRIP_DATE_END, 0L),
                bundle.getString(EXTRA_TRIP_DESTINATION, "")
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)

        _tripTitleTextView.text = tripFromExtra.title
        _tripDestinationTextView.text = tripFromExtra.destination


        /**
         * On utilise un SimpleDateFormat et un format, ici "dd/MM/yyyy HH:mm:ss" pour générer des string lisible à partir des dates en timestamp milliseconds. (des Long du genre 1580155641)
         */
        var dateRangeString = "Du "
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        dateRangeString += sdf.format(tripFromExtra.dateStart)
        dateRangeString += " au "
        dateRangeString += sdf.format(tripFromExtra.dateEnd)

        _tripDateRangeTextView.text = dateRangeString
    }
}