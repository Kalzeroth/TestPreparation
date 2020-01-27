package bruno.cci.testpreparation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bruno.cci.testpreparation.R
import kotlinx.android.synthetic.main.list_item_string.view.*

/**
 * Classe qui va permettre d'afficher une série de textes au format String.
 * L'adapter va prendre une liste de String en paramètre et sera utilisé sur une RecyclerView dans un fragment ou une activité.
 */
class StringAdapter(private val elements: List<String>) :
    RecyclerView.Adapter<StringAdapter.StringViewHolder>() {

    /**
     * Création d'un ViewHolder avec une View en paramètre.
     * La view est crée à partir d'un layout xml (list_item_string) grâce au LayoutInflater.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): StringViewHolder = StringViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_string,
            parent,
            false
        )
    )

    /**
     * Doit représenter le nombre d'élements à afficher dans la liste
     */
    override fun getItemCount(): Int = elements.size

    /**
     * Action a effectuer sur chaque vue avant qu'elle soit affichée.
     * On y donne donc pour chaque position l'élement de la liste qui correspond.
     */
    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bindView(elements[position])
    }

    /**
     * Classe uniquement utilisée dans l'adapter, donc on peut la créer içi.
     * Elle représente une cellule de la liste.
     *
     * @see onCreateViewHolder La cellule va être remplie par la vue créer dans la méthode onCreateViewHolder.
     *
     * @see onBindViewHolder  La méthode onBindViewHolder est utilisée pendant le défilement notamment pour créer à la volée les éléments qui doivent être affichées.
     * Elle fonctionne en appelant la méthode bindView dans le StringViewHolder.
     */
    class StringViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        /**
         * On utilise la vue "view" en paramètre de la classe qui doit içi être le layout "list_item_string" pour y insérer les données.
         * Ici on met "element" un String, dans la vue.
         */
        fun bindView(element: String) {
            view._stringTextView.text = element
        }
    }
}