package bilek.pirateships.viewmodel

import bilek.pirateships.ItemDetailViewModel
import bilek.pirateships.model.PirateShip
import bilek.pirateships.repository.PirateShipsRepository
import kotlinx.coroutines.flow.flow
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyLong

class ItemDetailViewModelTest : BaseViewModelTest<ItemDetailViewModel>() {

  private val repository: PirateShipsRepository = mock()

  @Test
  fun whenPirateShipRequested_thenCorrectValuesSet(): Unit = runBlockingTest {

    // given

    val dummyPirateShip = createDummyPirateShip()

    `when`(repository.fetchPirateShipById(anyLong())).thenReturn(
            flow { emit(dummyPirateShip) }
    )

    val viewModel = createViewModel()

    viewModel.pirateShip.observeForever { }

    // when

    viewModel.requestPirateShip(0L)

    // then

    MatcherAssert.assertThat(viewModel.pirateShip.value, Is.`is`(dummyPirateShip))
  }

  @Test
  fun whenFabClicked_thenEventIsSent(): Unit = runBlockingTest {

    // given

    val dummyPirateShip = createDummyPirateShip()

    `when`(repository.fetchPirateShipById(anyLong())).thenReturn(
            flow { emit(dummyPirateShip) }
    )


    val viewModel = createViewModel()

    viewModel.requestPirateShip(0L)

    // when

    viewModel.onFabClick()

    // then

    MatcherAssert.assertThat(viewModel.clickEvent.value, Is.`is`("Ahoi!"))
  }

  override fun createViewModel() = ItemDetailViewModel(repository)

  private fun createDummyPirateShip() = PirateShip(
          0L,
          "",
          "",
          "",
          null,
          "Ahoi!"
  )
}
