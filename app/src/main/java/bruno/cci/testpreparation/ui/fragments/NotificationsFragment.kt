package bruno.cci.testpreparation.ui.fragments


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bruno.cci.testpreparation.R
import bruno.cci.testpreparation.changeFavoriteImage
import bruno.cci.testpreparation.models.Trip
import bruno.cci.testpreparation.share
import bruno.cci.testpreparation.ui.activities.TripActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.save
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.list_item_trip.*
import kotlinx.android.synthetic.main.list_item_trip.view.*


class NotificationsFragment :
    Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_notifications, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _closeCardBtn?.setOnClickListener {
            _tripCard?.visibility = View.GONE
        }

        val mapFragment = SupportMapFragment()

        childFragmentManager
            .beginTransaction()
            .replace(
                _googleMapContainer.id,
                mapFragment
            )
            .commit()

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(gMap: GoogleMap?) {
        googleMap = gMap
        googleMap?.setOnMarkerClickListener(this)
        googleMap?.setOnMapClickListener {
            _tripCard.visibility = View.GONE
        }
        googleMap?.setOnCameraMoveListener {
            if (System.currentTimeMillis() -
                (lastTimeShowedTripCard ?: 0L) > 2000
            ) {
                _tripCard.visibility = View.GONE
            }
        }

        val boundsBuilder = LatLngBounds.builder()
        Trip().queryAll().forEach { trip ->
            if (trip.destination?.latitude != null &&
                trip.destination?.longitude != null
            ) {
                val position = LatLng(
                    trip.destination!!.latitude!!,
                    trip.destination!!.longitude!!
                )
                boundsBuilder.include(position)
                when (trip.title) {
                    "Test 1" -> {
                        googleMap?.addMarker(
                            MarkerOptions()
                                .position(position)
                                .title(trip.destination?.name).icon(
                                    BitmapDescriptorFactory
                                        .defaultMarker(80f)
                                )
                        )?.tag = trip
                    }
                    "Test 2" -> {
                        googleMap?.addMarker(
                            MarkerOptions()
                                .position(position)
                                .title(trip.destination?.name).icon(
                                    BitmapDescriptorFactory
                                        .defaultMarker(30f)
                                )
                        )?.tag = trip
                    }
                    else -> {
                        googleMap?.addMarker(
                            MarkerOptions()
                                .position(position)
                                .title(trip.destination?.name)
                                .icon(
                                    BitmapDescriptorFactory
                                        .defaultMarker(180f)
                                )
                        )?.tag = trip
                    }
                }

            }
        }
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 150)
        )
    }

    private var lastClickedMarker: Marker? = null
    private var lastTimeShowedTripCard: Long? = null

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker?.tag is Trip) {
            val trip = marker.tag as Trip
            if (context != null) {
                if (_tripCard.visibility == View.VISIBLE
                    && marker == lastClickedMarker
                ) {
                    _tripCard.visibility = View.GONE
                } else {
                    _tripCard.visibility = View.VISIBLE
                    lastTimeShowedTripCard = System.currentTimeMillis()
                    _listItemRoot.isClickable = false
                    _listItemRoot.background = null
                    _stringTextView?.setTextColor(Color.parseColor("#FFFFFF"))
                    _shareButton?.imageTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    _tripCard?.setOnClickListener {
                        if (activity != null)
                            TripActivity.start(activity!!, trip)
                    }
                    _stringTextView.text = trip.title
                    _favoriteButton.apply {
                        changeFavoriteImage(trip)
                        setOnClickListener {
                            trip.apply {
                                inFavorite = !(inFavorite ?: false)
                                changeFavoriteImage(this)
                                save()
                            }
                        }
                    }
                    _shareButton.setOnClickListener {
                        trip.share(it.context)
                    }
                }
                lastClickedMarker = marker
            }
            return false
        }
        return true
    }
}

