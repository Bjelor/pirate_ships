package bilek.pirateships

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import bilek.pirateships.databinding.ActivityItemDetailBinding
import bilek.pirateships.utilities.dependencyContainer

/**
 * Just realized this thing is completely bypassed on tablets, hence no FAB there, sorry -_-
 */
class ItemDetailActivity : AppCompatActivity() {

    private val viewModel: ItemDetailViewModel by viewModels {
        ItemDetailViewModel.Factory(dependencyContainer.pirateShipRepository)
    }

    private lateinit var binding: ActivityItemDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_detail)
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        if (savedInstanceState == null) {
            val id = intent.getLongExtra(ItemDetailFragment.ARG_ITEM_ID, -1)
            viewModel.requestPirateShip(id)

            showChildFragment()
        }

        setupObservers()
    }

    private fun showChildFragment() {
        val fragment = ItemDetailFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.item_detail_container, fragment)
            .commit()
    }

    private fun setupObservers() {
        viewModel.clickEvent.observe(this) { text ->
            showGreetingAlertDialog(text)
        }
    }

    private fun showGreetingAlertDialog(text: String) {
        AlertDialog.Builder(this@ItemDetailActivity)
            .setMessage(text)
            .setCancelable(true)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog?.cancel()
            }
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, ItemListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
