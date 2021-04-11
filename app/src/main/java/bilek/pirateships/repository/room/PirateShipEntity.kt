package bilek.pirateships.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pirate_ship")
data class PirateShipEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val price: Int,
    val image: String?,
    @ColumnInfo(name = "greeting_type")
    val greetingType: String? = null,
)
