package bilek.pirateships.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PirateShip(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val image: String?,
    val greeting: String,
)
