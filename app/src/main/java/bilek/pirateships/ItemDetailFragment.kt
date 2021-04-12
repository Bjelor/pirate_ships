package bilek.pirateships

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import bilek.pirateships.databinding.ItemDetailBinding
import bilek.pirateships.utilities.dependencyContainer
import com.bumptech.glide.Glide

class ItemDetailFragment : Fragment() {

    private val viewModel: ItemDetailViewModel by activityViewModels {
        ItemDetailViewModel.Factory(dependencyContainer.pirateShipRepository)
    }

    private lateinit var binding: ItemDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ItemDetailBinding.inflate(inflater, container, false)

    return binding.root
  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupArguments()
        setupObservers()
    }

    private fun setupArguments() {
        binding.image.transitionName = "image_${arguments?.getLong(ARG_ITEM_ID)}"

        if (requireActivity() is ItemListActivity) {
            arguments?.let {
                if (it.containsKey(ARG_ITEM_ID)) {
                    viewModel.requestPirateShip(it.getLong(ARG_ITEM_ID, -1))
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.pirateShip.observe(viewLifecycleOwner) { pirateShip ->
            if (pirateShip != null) {
                Glide.with(requireContext()).load(pirateShip.image).into(binding.image)
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
