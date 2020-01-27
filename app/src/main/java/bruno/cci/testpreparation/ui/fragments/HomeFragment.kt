package bruno.cci.testpreparation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bruno.cci.testpreparation.R
import bruno.cci.testpreparation.TripClickedCallback
import bruno.cci.testpreparation.ui.activities.TripActivity
import bruno.cci.testpreparation.ui.adapters.TripAdapter
import bruno.cci.testpreparation.ui.models.Trip
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    /**
     * Fonction hérité de la classe Fragment.
     * Elle sera appellée automatiquement quand la vue sera prête avec la méthode onCreateView.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * "_tripRecyclerView" est la vue du layout actuellement affiché : "fragment_home" de type RecyclerView
         */
        _tripRecyclerView.apply {
            /**
             * On donne toujours ces deux choses aux RecyclerView, un layoutManager et un adapter.
             * Le layoutManager défini la hiérarchie entre les vue qui seront créer par l'adapter.
             *
             * @see LinearLayoutManager empile les vue les unes sur les autres.
             * @see GridLayoutManager permet de faire des colonnes.
             *
             * L'adapter est créer par nos soins, dans le package (dossier) qui s'appel "adapters".
             * @see TripAdapter pour un adapter avec un callback permettant de contrôler le click depuis le fragment.
             */
            layoutManager = LinearLayoutManager(context)

            /** On peut écrire le dernier argument d'une fonction ou d'un constructeur comme ci dessous, donc entre {} (en bloc) si il s'agit d'un callback/fonction/lambda (les trois sont la même chose)
             *
             * Ici le dernier argument attendu dans le TripAdapter est un TripClickedCallback défini dans le fichier utils comme ceci :
             * typealias TripClickedCallback = (Trip) -> Unit
             * @see TripClickedCallback
             * La fonction qu'on va définir et qui sera utilisé dans l'adapter devra donc être appelé avec un Trip en paramètre, qu'on peut voir répercuté ci dessous : tripClicked ->
             */
            adapter = TripAdapter(
                listOf(
                    Trip(
                        "Test 1",
                        System.currentTimeMillis() + 100000,
                        System.currentTimeMillis() + 500000,
                        "Colmar"
                    ),
                    Trip(
                        "Test 2",
                        System.currentTimeMillis() + 200000,
                        System.currentTimeMillis() + 800000,
                        "Strasbourg"
                    ),
                    Trip(
                        "Test 3",
                        System.currentTimeMillis() + 600000,
                        System.currentTimeMillis() + 1000000,
                        "Mulhouse"
                    )
                )
            ) { tripClicked ->

                /**
                 * Méthode statique de l'activité TripActivity pour l'ouvrir et afficher le détail d'un Trip
                 */
                TripActivity.start(context, tripClicked)
            }
        }
    }
}