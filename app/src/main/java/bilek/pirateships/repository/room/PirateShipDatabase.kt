package bilek.pirateships.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [PirateShipEntity::class], version = 1)
abstract class PirateShipDatabase : RoomDatabase() {

    abstract fun pirateShipDao(): PirateShipDao
}
