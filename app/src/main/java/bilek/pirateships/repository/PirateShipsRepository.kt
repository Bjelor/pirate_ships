package bilek.pirateships.repository

import bilek.pirateships.model.PirateShip
import bilek.pirateships.model.factory.PirateShipFactory
import bilek.pirateships.repository.room.PirateShipDao
import bilek.pirateships.repository.room.PirateShipEntity
import bilek.pirateships.repository.room.PirateShipEntityFactory
import bilek.pirateships.repository.service.PirateShipService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PirateShipsRepository(
        private val pirateShipsService: PirateShipService,
        private val pirateShipDao: PirateShipDao,
        private val pirateShipFactory: PirateShipFactory,
        private val pirateShipEntityFactory: PirateShipEntityFactory,
) {

  fun fetchPirateShipById(id: Long): Flow<PirateShip?> = flow {
    val shipEntity = pirateShipDao.getById(id) ?: downloadPirateShips().find { it.id == id }
    val ship = shipEntity?.let { pirateShipFactory.fromEntity(it) }

    emit(ship)
  }.flowOn(Dispatchers.IO)

  fun fetchPirateShips(): Flow<List<PirateShip>> = flow {
    val shipEntities = pirateShipDao.getAll().takeIf { it.isNotEmpty() }
            ?: downloadPirateShips()
    val ships = pirateShipFactory.fromEntities(shipEntities)

    emit(ships)
  }.flowOn(Dispatchers.IO)

  private suspend fun downloadPirateShips(): List<PirateShipEntity> {
    val response = pirateShipsService.getPirateShips()
    val ships = pirateShipEntityFactory.fromResponse(response)

    pirateShipDao.insertAll(ships)

    return ships
  }
}
