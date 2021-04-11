package bilek.pirateships

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import bilek.pirateships.model.PirateShip
import bilek.pirateships.repository.PirateShipsRepository
import bilek.pirateships.utilities.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *This fella doesn't really need to be shared between ItemDetailActivity & ItemDetailFragment
 * but I've never used shared view models before, so I had to try!
 */
class ItemDetailViewModel(private val pirateShipsRepository: PirateShipsRepository) : ViewModel() {

  private val pirateShipFlow: MutableStateFlow<PirateShip?> = MutableStateFlow(null)

  val pirateShip: LiveData<PirateShip?> = pirateShipFlow
          .asLiveData()

  val clickEvent: SingleLiveEvent<String> = SingleLiveEvent()

  fun onFabClick() {
    clickEvent.value = pirateShipFlow.value?.greeting
  }

  fun requestPirateShip(id: Long) {
    if (id < 0) {
      return
    }

    viewModelScope.launch {
      pirateShipsRepository.fetchPirateShipById(id).collect { pirateShip ->
        pirateShipFlow.value = pirateShip
      }
    }
  }

  class Factory(
          private val pirateShipsRepository: PirateShipsRepository,
  ) :
          ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            ItemDetailViewModel(pirateShipsRepository) as T
  }
}
