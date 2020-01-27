package bruno.cci.testpreparation.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import bruno.cci.testpreparation.R
import bruno.cci.testpreparation.models.Trip
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import kotlinx.android.synthetic.main.activity_trip.*
import java.text.SimpleDateFormat
import java.util.*

class TripActivity : AppCompatActivity() {

    /**
     * Le companion object défini les méthodes statiques (qui peuvent être appelées sans avoir d'instance de la classe)
     */
    companion object {
        private const val EXTRA_TRIP_TITLE = "EXTRA_TRIP_TITLE"
        const val TRIP_REQUEST_CODE = 1111

        /**
         * Méthode statique ouvrir cette activité et afficher le détail d'un Trip
         * Trip n'est pas envoyé, on envois juste la PrimaryKey défini dans le modèle, qui est unique à chaque objet.
         */
        fun start(activity: Activity, trip: Trip) {
            activity.startActivityForResult(
                Intent(activity, TripActivity::class.java).apply {
                    putExtra(EXTRA_TRIP_TITLE, trip.title)
                },
                TRIP_REQUEST_CODE
            )
        }
    }

    /**
     * Méthode simple pour récupérer dans une variable un Trip récupérer de la base de donnée grâce à l'ID.
     */
    private val tripFromExtra by lazy {
        Trip().queryFirst {
            equalTo(
                "title",
                intent?.extras?.getString(EXTRA_TRIP_TITLE, "")
            )
        }
            ?: Trip()
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


        _favoriteButton.apply {
            changeImage()

            setOnClickListener {
                tripFromExtra.apply {
                    inFavorite = !(inFavorite ?: false)
                    changeImage()
                    save()
                }
            }
        }
    }

    /**
     * Donne la bonne image et la bonne couleur au coeur pour mettre en favoris selon l'état actuel.
     */
    private fun ImageView.changeImage() {
        imageTintList = if (tripFromExtra.inFavorite == true) {
            setImageResource(R.drawable.ic_favorite_black_24dp)
            ColorStateList.valueOf(Color.parseColor("#BB3030"))
        } else {
            setImageResource(R.drawable.ic_favorite_border_black_24dp)
            ColorStateList.valueOf(Color.parseColor("#BB8030"))
        }
    }
}