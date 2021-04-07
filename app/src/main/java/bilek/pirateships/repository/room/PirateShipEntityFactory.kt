package bilek.pirateships.repository.room

import bilek.pirateships.model.network.PirateShipResponse

class PirateShipEntityFactory {

    fun fromResponse(response: PirateShipResponse): List<PirateShipEntity> {
        return response.ships.mapNotNull { responseShip ->
            if (responseShip != null) {
                PirateShipEntity(
                    responseShip.id,
                    responseShip.title ?: "",
                    responseShip.description,
                    responseShip.price,
                    responseShip.image,
                    responseShip.greetingType,
                )
            } else {
                null
            }
        }
    }
}