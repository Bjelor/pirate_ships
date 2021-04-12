package bilek.pirateships

import androidx.lifecycle.*
import bilek.pirateships.model.PirateShip
import bilek.pirateships.repository.PirateShipsRepository
import bilek.pirateships.utilities.SingleLiveEvent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn

class ItemListViewModel(pirateShipsRepository: PirateShipsRepository) : ViewModel() {

    private val pirateShipsFlow: StateFlow<List<PirateShip>> =
        pirateShipsRepository.fetchPirateShips()
            .onCompletion { isLoading.value = false }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

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
