package bruno.cci.testpreparation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bruno.cci.testpreparation.R
import bruno.cci.testpreparation.TripClickedCallback
import bruno.cci.testpreparation.ui.models.Trip
import kotlinx.android.synthetic.main.list_item_trip.view.*

/**
 * Classe qui va permettre d'afficher le titre d'une série de voyages.
 * L'adapter va prendre une liste de Trip en paramètre et sera utilisé sur une RecyclerView dans un fragment ou une activité.
 *
 * @see TripClickedCallback
 * On lui passe aussi un callback, donc une fonction qui va être exécutée lorsqu'on le souhaite.
 * Ici on va l'utilisé lorsque la vue d'un Trip est cliquée.
 */
class TripAdapter(private val elements: List<Trip>, private val tripClickedCallback: TripClickedCallback) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    /**
     * Création d'un ViewHolder avec une View en paramètre.
     * La view est crée à partir d'un layout xml (list_item_trip) grâce au LayoutInflater.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TripViewHolder = TripViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_trip,
            parent,
            false
        ),
        tripClickedCallback
    )

    /**
     * Doit représenter le nombre d'élements à afficher dans la liste
     */
    override fun getItemCount(): Int = elements.size

    /**
     * Action a effectuer sur chaque vue avant qu'elle soit affichée.
     * On y donne donc pour chaque position l'élement de la liste qui correspond.
     */
    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bindView(elements[position])
    }

    /**
     * Classe uniquement utilisée dans l'adapter, donc on peut la créer içi.
     * Elle représente une cellule de la liste.
     *
     * @see onCreateViewHolder La cellule va être remplie par la vue créer dans la méthode onCreateViewHolder.
     *
     * @see onBindViewHolder  La méthode onBindViewHolder est utilisée pendant le défilement notamment pour créer à la volée les éléments qui doivent être affichées.
     * Elle fonctionne en appelant la méthode bindView dans le TripViewHolder.
     */
    class TripViewHolder(
        private val view: View,
        private val tripClickedCallback: TripClickedCallback
    ) : RecyclerView.ViewHolder(view) {

        /**
         * On utilise la vue "view" en paramètre de la classe qui doit içi être le layout "list_item_trip" pour y insérer les données.
         * Ici on met "element" un String, dans la vue.
         */
        fun bindView(element: Trip) {
            view._stringTextView.text = element.title
            view.setOnClickListener {
                tripClickedCallback.invoke(element)
            }
        }
    }
}