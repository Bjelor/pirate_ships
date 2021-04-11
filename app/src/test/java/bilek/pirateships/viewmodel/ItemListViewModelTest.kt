package bilek.pirateships.viewmodel

import bilek.pirateships.ItemListViewModel
import bilek.pirateships.model.PirateShip
import bilek.pirateships.repository.PirateShipsRepository
import kotlinx.coroutines.flow.flow
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Test
import org.mockito.Mockito.`when`

class ItemListViewModelTest : BaseViewModelTest<ItemListViewModel>() {

  private val repository: PirateShipsRepository = mock()

  @Test
  fun whenOnItemClickCalled_thenEventIsSent(): Unit = runBlockingTest {

    // given

    val viewModel = createViewModel()

    // when

    viewModel.onItemClick(15L)

    // then

    MatcherAssert.assertThat(viewModel.clickEvent.value, `is`(15L))
  }

  @Test
  fun whenPirateShipsAreBeingLoaded_thenIsLoading(): Unit = runBlockingTest {

    // given

    `when`(repository.fetchPirateShips()).thenReturn(
            flow { emit(emptyList<PirateShip>()) }
    )

    // when

    val viewModel = createViewModel()

    // then

    MatcherAssert.assertThat(viewModel.isLoading.value, `is`(true))

    // when

    viewModel.pirateShips.observeForever { }

    // then

    MatcherAssert.assertThat(viewModel.isLoading.value, `is`(false))
  }

  override fun createViewModel() = ItemListViewModel(repository)
}
