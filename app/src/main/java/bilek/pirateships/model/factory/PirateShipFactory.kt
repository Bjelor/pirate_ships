package bilek.pirateships.model.factory

import bilek.pirateships.model.PirateShip
import bilek.pirateships.model.network.PirateShipResponse
import bilek.pirateships.repository.room.PirateShipEntity

class PirateShipFactory {

    fun fromResponse(response: PirateShipResponse): List<PirateShip> {
        return response.ships.mapNotNull { responseShip ->
            if (responseShip != null) {
                PirateShip(
                    responseShip.id,
                    responseShip.title ?: "",
                    responseShip.description,
                    responseShip.price.toString(),
                    responseShip.image,
                    createGreeting(responseShip.greetingType),
                )
            } else {
                null
            }
        }
    }

    fun fromEntities(entities: List<PirateShipEntity>): List<PirateShip> =
        entities.map { fromEntity(it) }

    fun fromEntity(entity: PirateShipEntity): PirateShip =
        PirateShip(
            entity.id,
            entity.title ?: "",
            entity.description,
            entity.price.toString(),
            entity.image,
            createGreeting(entity.greetingType),
        )

    private fun createGreeting(from: String?): String = when (from) {
        "ah" -> "Ahoi!"
        "ay" -> "Aye Aye!"
        "ar" -> "Arrr!"
        "yo" -> "Yo ho hooo!"
        // You guys meant this by "default" right? Otherwise this would just return null
        // and the onClickListener would do a null check before showing the alert.
        else -> "Ahoi!"
    }
}
