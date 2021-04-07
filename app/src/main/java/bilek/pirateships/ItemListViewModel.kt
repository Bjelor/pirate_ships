package bilek.pirateships

import androidx.lifecycle.*
import bilek.pirateships.model.PirateShip
import bilek.pirateships.utilities.SingleLiveEvent
import bilek.pirateships.repository.PirateShipsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ItemListViewModel(pirateShipsRepository: PirateShipsRepository) : ViewModel() {

    private val pirateShipsFlow: StateFlow<List<PirateShip>> =
        pirateShipsRepository.fetchPirateShips()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val pirateShips: LiveData<List<PirateShip>> = pirateShipsFlow
        .asLiveData()

    val clickEvent: SingleLiveEvent<Long> = SingleLiveEvent()

    fun onItemClick(id: Long) {
        clickEvent.value = id
    }

    class Factory(
        private val pirateShipsRepository: PirateShipsRepository,
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            ItemListViewModel(pirateShipsRepository) as T
    }
}
