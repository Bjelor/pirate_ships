package bilek.pirateships

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import bilek.pirateships.model.PirateShip
import bilek.pirateships.utilities.dependencyContainer

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private val viewModel: ItemListViewModel by viewModels {
        ItemListViewModel.Factory(dependencyContainer.pirateShipRepository)
    }

    private val adapter: PirateShipAdapter by lazy {
        PirateShipAdapter(this, viewModel::onItemClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(findViewById(R.id.item_list))
        setupObservers()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.pirateShips.observe(this) { pirateShips ->
            adapter.values = pirateShips
        }

        viewModel.clickEvent.observe(this) { id ->
            openDetail(id)
        }
    }

    private fun openDetail(id: Long) {
        if (twoPane) {
            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ItemDetailFragment.ARG_ITEM_ID, id)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, ItemDetailActivity::class.java).apply {
                putExtra(ItemDetailFragment.ARG_ITEM_ID, id)
            }
            startActivity(intent)
        }
    }
}
