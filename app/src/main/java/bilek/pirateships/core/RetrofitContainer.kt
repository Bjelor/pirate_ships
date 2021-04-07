package bilek.pirateships.core

import bilek.pirateships.service.PirateShipService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitContainer {

    companion object {
        private const val BASE_URL = "https://assets.shpock.com/mobile/interview-test/"
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val pirateShipsService: PirateShipService = retrofit.create(PirateShipService::class.java)

}