package bilek.pirateships.repository

import bilek.pirateships.BaseTest
import bilek.pirateships.model.PirateShip
import bilek.pirateships.model.factory.PirateShipFactory
import bilek.pirateships.repository.room.PirateShipDao
import bilek.pirateships.repository.room.PirateShipEntity
import bilek.pirateships.repository.room.PirateShipEntityFactory
import bilek.pirateships.repository.service.PirateShipService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.*

class PirateShipRepositoryTest : BaseTest() {

  private val pirateShipsService: PirateShipService = mock()
  private val pirateShipDao: PirateShipDao = mock()
  private val pirateShipFactory: PirateShipFactory = mock()
  private val pirateShipEntityFactory: PirateShipEntityFactory = mock()

  @Test
  fun whenFetchPirateShipsCalledAndDatabaseIsEmpty_thenPirateShipsAreDownloadedAndStored(): Unit =
          runBlockingTest {

            // given

            val repository = createRepository()

            `when`(pirateShipDao.getAll()).thenReturn(emptyList())
            `when`(pirateShipsService.getPirateShips()).thenReturn(mock())
            `when`(pirateShipEntityFactory.fromResponse(any()))
                    .thenReturn(listOf(mock()))
            `when`(pirateShipFactory.fromEntities(anyList())).thenReturn(listOf(mock()))

            // when

            var result: List<PirateShip>? = null

            runBlocking {
              repository.fetchPirateShips().collect {
                result = it
              }
            }

            // then

            verify(pirateShipDao).getAll()
            verify(pirateShipsService).getPirateShips()
            verify(pirateShipDao).insertAll(anyList())

            MatcherAssert.assertThat(result?.size, `is`(1))
          }

  @Test
  fun whenFetchPirateShipsCalledAndDatabaseHasData_thenPirateShipsAreNotDownloaded(): Unit =
          runBlockingTest {

            // given

            val repository = createRepository()

            `when`(pirateShipDao.getAll()).thenReturn(listOf(mock()))
            `when`(pirateShipFactory.fromEntities(anyList())).thenReturn(listOf(mock()))

            // when

            var result: List<PirateShip>? = null

            runBlocking {
              repository.fetchPirateShips().collect {
                result = it
              }
            }

            // then

            verifyZeroInteractions(pirateShipsService)
            verify(pirateShipDao).getAll()

            MatcherAssert.assertThat(result?.size, `is`(1))
          }

  @Test
  fun whenFetchPirateShipByIdCalledAndDatabaseHasData_thenPirateShipsAreNotDownloaded(): Unit =
          runBlockingTest {

            // given

            val repository = createRepository()

            `when`(pirateShipDao.getById(eq(1L))).thenReturn(mock())
            `when`(pirateShipFactory.fromEntity(any())).thenReturn(mock())

            // when

            var result: PirateShip? = null

            runBlocking {
              repository.fetchPirateShipById(1L).collect {
                result = it
              }
            }

            // then

            verifyZeroInteractions(pirateShipsService)
            verify(pirateShipDao).getById(eq(1L))

            Assert.assertNotNull(result)
          }

  @Test
  fun whenFetchPirateShipByIdCalledAndDatabaseIsEmpty_thenPirateShipsAreDownloaded(): Unit =
          runBlockingTest {

            // given

            val repository = createRepository()
            val dummyPirateShip = PirateShipEntity(1L, "", "", 0, null)

            `when`(pirateShipDao.getById(eq(1L))).thenReturn(null)
            `when`(pirateShipsService.getPirateShips()).thenReturn(mock())
            `when`(pirateShipEntityFactory.fromResponse(any()))
                    .thenReturn(listOf(dummyPirateShip))
            `when`(pirateShipFactory.fromEntity(any()))
                    .thenReturn(mock())

            // when

            var result: PirateShip? = null

            runBlocking {
              repository.fetchPirateShipById(1L).collect {
                result = it
              }
            }

            // then

            verify(pirateShipDao).getById(eq(1L))
            verify(pirateShipsService).getPirateShips()
            verify(pirateShipDao).insertAll(anyList())

            Assert.assertNotNull(result)
          }

  private fun createRepository() = PirateShipsRepository(
          pirateShipsService,
          pirateShipDao,
          pirateShipFactory,
          pirateShipEntityFactory,
  )
}
