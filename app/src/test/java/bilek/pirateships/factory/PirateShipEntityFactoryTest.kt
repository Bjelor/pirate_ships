package bilek.pirateships.factory

import bilek.pirateships.model.network.PirateShipResponse
import bilek.pirateships.repository.room.PirateShipEntity
import bilek.pirateships.repository.room.PirateShipEntityFactory
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Test

class PirateShipEntityFactoryTest : BaseFactoryTest<PirateShipEntityFactory>() {

  @Test
  fun whenPirateShipsCreatedFromResponse_thenResultsAreCorrect() {

    // given

    val dummyResponse = PirateShipResponse(
            true,
            listOf(
                    PirateShipResponse.PirateShip(
                            0L, "pirate_ship1", "description1", 125, "image_url1", "ah"
                    ),
                    PirateShipResponse.PirateShip(
                            1L, null, "description2", 225, null, "ay"
                    ),
                    PirateShipResponse.PirateShip(
                            2L, "pirate_ship3", "description3", 325, "image_url3", "ar"
                    ),
                    PirateShipResponse.PirateShip(
                            3L, "pirate_ship4", "description4", 425, "image_url4", "yo"
                    ),
                    PirateShipResponse.PirateShip(
                            4L, "pirate_ship5", "description5", 525, "image_url5", null
                    ),
            ),
    )

    val factory = createFactory()

    // when

    val results = factory.fromResponse(dummyResponse)

    // then

    MatcherAssert.assertThat(results.size, Is.`is`(5))
    MatcherAssert.assertThat(
            results[0],
            Is.`is`(PirateShipEntity(
                    0L, "pirate_ship1", "description1", 125, "image_url1", "ah"
            ))
    )
    MatcherAssert.assertThat(
            results[1],
            Is.`is`(PirateShipEntity(
                    1L, "", "description2", 225, null, "ay"
            ))
    )
    MatcherAssert.assertThat(
            results[2],
            Is.`is`(PirateShipEntity(
                    2L, "pirate_ship3", "description3", 325, "image_url3", "ar"
            ))
    )
    MatcherAssert.assertThat(
            results[3],
            Is.`is`(PirateShipEntity(
                    3L, "pirate_ship4", "description4", 425, "image_url4", "yo"
            ))
    )
    MatcherAssert.assertThat(
            results[4],
            Is.`is`(PirateShipEntity(
                    4L, "pirate_ship5", "description5", 525, "image_url5", null
            ))
    )
  }

  override fun createFactory() = PirateShipEntityFactory()
}
