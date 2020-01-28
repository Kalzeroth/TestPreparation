package bruno.cci.testpreparation

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import bruno.cci.testpreparation.models.Trip

typealias TripClickedCallback = (Trip) -> Unit
typealias TripRemovedFromFavoriteCallback = (Trip) -> Unit

fun ImageView.changeFavoriteImage(trip: Trip) {
    imageTintList = if (trip.inFavorite == true) {
        setImageResource(R.drawable.ic_favorite_black_24dp)
        ColorStateList.valueOf(Color.parseColor("#BB3030"))
    } else {
        setImageResource(R.drawable.ic_favorite_border_black_24dp)
        ColorStateList.valueOf(Color.parseColor("#BB8030"))
    }
}

fun Trip.share(context: Context) {
    context.startActivity(
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "J'ai visité ${destination?.name}, c'était génial !")
            type = "text/plain"
        }
    )
}
