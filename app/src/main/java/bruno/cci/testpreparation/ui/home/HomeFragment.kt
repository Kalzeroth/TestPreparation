package bruno.cci.testpreparation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bruno.cci.testpreparation.R
import bruno.cci.testpreparation.ui.adapters.StringAdapter
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
         * "_stringRecyclerView" est la vue du layout actuellement affiché : "fragment_home" de type RecyclerView
         */
        _stringRecyclerView.apply {
            /**
             * On donne toujours ces deux choses aux RecyclerView, un layoutManager et un adapter.
             * Le layoutManager défini la hiérarchie entre les vue qui seront créer par l'adapter.
             *
             * @see LinearLayoutManager empile les vue les unes sur les autres.
             * @see GridLayoutManager permet de faire des colonnes.
             *
             * L'adapter est créer par nos soins, dans le package (dossier) qui s'appel "adapters".
             * @see StringAdapter pour un adapter très basique.
             */
            layoutManager = LinearLayoutManager(context)
            adapter = StringAdapter(listOf("Test 1", "Test 2", "Test 3"))
        }
    }
}