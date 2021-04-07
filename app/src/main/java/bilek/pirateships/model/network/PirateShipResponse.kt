package bilek.pirateships.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// The Task spec doesn't provide API specs, so this class is mostly guessed.
// Things aren't nullable unless they're null somewhere in the data or they're optional.
@JsonClass(generateAdapter = true)
data class PirateShipResponse(
    val success: Boolean,
    val ships: List<PirateShip?>,
) {

    @JsonClass(generateAdapter = true)
    data class PirateShip(
        val id: Long,
        val title: String?,
        val description: String,
        val price: Int,
        val image: String?,
        @Json(name = "greeting_type")
        val greetingType: String? = null,
    )
}
