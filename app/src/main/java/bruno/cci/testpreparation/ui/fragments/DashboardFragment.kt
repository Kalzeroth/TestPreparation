package bruno.cci.testpreparation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bruno.cci.testpreparation.R
import bruno.cci.testpreparation.adapters.TripAdapter
import bruno.cci.testpreparation.models.Trip
import bruno.cci.testpreparation.ui.activities.MainActivity
import bruno.cci.testpreparation.ui.activities.TripActivity
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import kotlinx.android.synthetic.main.fragment_home.*

class DashboardFragment : Fragment(), RefreshableFragment {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _tripRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            refreshList()
        }
    }

    override fun refreshList() {
        _tripRecyclerView.adapter = TripAdapter(
            Trip().query { equalTo("inFavorite", true) }.toMutableList(),
            true
        ) { tripClicked ->
            if (activity != null)
                TripActivity.start((activity!! as MainActivity), tripClicked)
        }
    }
}