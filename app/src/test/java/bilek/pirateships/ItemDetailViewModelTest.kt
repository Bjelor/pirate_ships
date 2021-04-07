package bilek.pirateships

import bilek.pirateships.model.PirateShip
import bilek.pirateships.repository.PirateShipsRepository
import kotlinx.coroutines.flow.flow
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyLong

class ItemDetailViewModelTest: BaseTest() {

    private val repository: PirateShipsRepository = mock()

    @Test
    fun whenPirateShipRequested_thenCorrectValuesSet(): Unit = runBlockingTest {

        // given

        val dummyPirateShip = PirateShip(
            0L,
            "",
            "",
            "",
            null,
            "Ahoi!"
        )

        `when`(repository.fetchPirateShipById(anyLong())).thenReturn(
            flow { emit(dummyPirateShip) }
        )

        val viewModel = ItemDetailViewModel(repository)

        viewModel.pirateShip.observeForever {  }

        // when

        viewModel.requestPirateShip(0L)

        // then

        MatcherAssert.assertThat(viewModel.pirateShip.value, Is.`is`(dummyPirateShip))
    }

    @Test
    fun whenOnItemClickCalled_thenEventIsSent(): Unit = runBlockingTest {

        // given

        val dummyPirateShip = PirateShip(
            0L,
            "",
            "",
            "",
            null,
            "Ahoi!"
        )

        `when`(repository.fetchPirateShipById(anyLong())).thenReturn(
            flow { emit(dummyPirateShip) }
        )


        val viewModel = ItemDetailViewModel(repository)

        viewModel.requestPirateShip(0L)

        // when

        viewModel.onFabClick()

        // then

        MatcherAssert.assertThat(viewModel.clickEvent.value, Is.`is`("Ahoi!"))
    }

}
