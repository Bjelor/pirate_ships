package bilek.pirateships.core

import android.app.Application
import androidx.room.Room
import bilek.pirateships.model.factory.PirateShipFactory
import bilek.pirateships.repository.PirateShipsRepository
import bilek.pirateships.repository.room.PirateShipDatabase
import bilek.pirateships.repository.room.PirateShipEntityFactory
import bilek.pirateships.repository.service.PirateShipService

// Dagger or Koin would be better options but I've never tried to do this manually, so there you go.
class DependencyContainer(application: Application) {

    private val retrofitContainer: RetrofitContainer = RetrofitContainer()

    private val pirateShipsService: PirateShipService = retrofitContainer.pirateShipsService

    private val pirateShipDatabase: PirateShipDatabase = Room.databaseBuilder(
        application,
        PirateShipDatabase::class.java,
        "pirate-ship-database"
    ).build()

    private val pirateShipFactory: PirateShipFactory = PirateShipFactory()

    private val pirateShipEntityFactory: PirateShipEntityFactory = PirateShipEntityFactory()

    val pirateShipRepository: PirateShipsRepository = PirateShipsRepository(
        pirateShipsService,
        pirateShipDatabase.pirateShipDao(),
        pirateShipFactory,
        pirateShipEntityFactory,
    )
}
