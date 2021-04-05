package bilek.pirateships.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PirateShip(
    val id: Long,
    val title: String,
    val description: String,
    val price: Int,
    val image: String,
    val greeting_type: String, // TODO: Make a custom adapter and map this to an enum.
)
