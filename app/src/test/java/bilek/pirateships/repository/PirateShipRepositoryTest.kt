package bilek.pirateships.repository

import bilek.pirateships.BaseTest
import bilek.pirateships.model.PirateShip
import bilek.pirateships.model.factory.PirateShipFactory
import bilek.pirateships.model.network.PirateShipResponse
import bilek.pirateships.repository.room.PirateShipDao
import bilek.pirateships.repository.room.PirateShipEntityFactory
import bilek.pirateships.repository.service.PirateShipService
import kotlinx.coroutines.flow.collect
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Test
import org.mockito.Mockito.*

class PirateShipRepositoryTest : BaseTest() {

  private val pirateShipsService: PirateShipService = mock()
  private val pirateShipDao: PirateShipDao = mock()
  private val pirateShipFactory: PirateShipFactory = mock()
  private val pirateShipEntityFactory: PirateShipEntityFactory = mock()

  @Test
  fun whenFetchPirateShipsCalledAndDatabaseIsEmpty_thenPirateShipsAreDownloaded(): Unit =
          runBlockingTest {

            // given

            val repository = createRepository()
            val dummyResponse = PirateShipResponse(true, listOf(mock()))

            `when`(pirateShipDao.getAll()).thenReturn(emptyList())
            `when`(pirateShipsService.getPirateShips()).thenReturn(dummyResponse)
            `when`(pirateShipEntityFactory.fromResponse(any())).thenReturn(listOf(mock()))

            // when

            var result: List<PirateShip>? = null

            repository.fetchPirateShips().collect {
              result = it
            }

            // then

            verify(pirateShipDao).getAll()
            verify(pirateShipsService).getPirateShips()
            verify(pirateShipEntityFactory).fromResponse(any())
            verify(pirateShipDao).insertAll(anyList())
            verify(pirateShipFactory).fromEntities(anyList())

            MatcherAssert.assertThat(result?.size, `is`(1))
          }

  private fun createRepository() = PirateShipsRepository(
          pirateShipsService,
          pirateShipDao,
          pirateShipFactory,
          pirateShipEntityFactory,
  )
}
