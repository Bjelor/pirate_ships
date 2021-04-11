package bilek.pirateships.repository.service

import bilek.pirateships.model.network.PirateShipResponse
import retrofit2.http.GET

interface PirateShipService {

    @GET("pirateships")
    suspend fun getPirateShips(): PirateShipResponse
}
