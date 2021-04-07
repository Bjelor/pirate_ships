package bilek.pirateships.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PirateShipDao {

    @Query("SELECT * FROM pirate_ship")
    fun getAll(): List<PirateShipEntity>

    @Query("SELECT * FROM pirate_ship WHERE id IS (:id)")
    fun getById(id: Long): PirateShipEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pirateShips: List<PirateShipEntity>)
}
