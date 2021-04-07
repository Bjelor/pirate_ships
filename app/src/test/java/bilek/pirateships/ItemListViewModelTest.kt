package bilek.pirateships

import bilek.pirateships.repository.PirateShipsRepository
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Test

class ItemListViewModelTest: BaseTest() {

    private val repository: PirateShipsRepository = mock()

    @Test
    fun whenOnItemClickCalled_thenEventIsSent(): Unit = runBlockingTest {

        // given

        val viewModel = ItemListViewModel(repository)

        // when

        viewModel.onItemClick(15L)

        // then

        MatcherAssert.assertThat(viewModel.clickEvent.value, `is`(15L))
    }

}
