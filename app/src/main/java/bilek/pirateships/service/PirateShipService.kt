package bilek.pirateships.service

import bilek.pirateships.model.network.PirateShipResponse
import retrofit2.http.GET

interface PirateShipService {

    @GET("pirateships")
    suspend fun getPirateShips(): PirateShipResponse
}
