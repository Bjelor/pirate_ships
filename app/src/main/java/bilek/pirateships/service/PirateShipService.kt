package bilek.pirateships.service

import bilek.pirateships.model.PirateShip
import retrofit2.http.GET

interface PirateShipService {

    @GET()
    suspend fun getPirateShips(): List<PirateShip>
}