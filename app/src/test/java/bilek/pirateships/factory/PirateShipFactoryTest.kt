package bilek.pirateships.factory

import bilek.pirateships.model.PirateShip
import bilek.pirateships.model.factory.PirateShipFactory
import bilek.pirateships.model.network.PirateShipResponse
import bilek.pirateships.repository.room.PirateShipEntity
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Test

class PirateShipFactoryTest : BaseFactoryTest<PirateShipFactory>() {

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

    MatcherAssert.assertThat(results.size, `is`(5))
    MatcherAssert.assertThat(
            results[0],
            `is`(PirateShip(0L, "pirate_ship1", "description1", "125", "image_url1", "Ahoi!"))
    )
    MatcherAssert.assertThat(
            results[1],
            `is`(PirateShip(1L, "", "description2", "225", null, "Aye Aye!"))
    )
    MatcherAssert.assertThat(
            results[2],
            `is`(PirateShip(2L, "pirate_ship3", "description3", "325", "image_url3", "Arrr!"))
    )
    MatcherAssert.assertThat(
            results[3],
            `is`(PirateShip(3L, "pirate_ship4", "description4", "425", "image_url4", "Yo ho hooo!"))
    )
    MatcherAssert.assertThat(
            results[4],
            `is`(PirateShip(4L, "pirate_ship5", "description5", "525", "image_url5", "Ahoi!"))
    )
  }

  @Test
  fun whenPirateShipsCreatedFromEntities_thenResultsAreCorrect() {

    // given

    val dummyEntities =
            listOf(
                    PirateShipEntity(
                            0L, "pirate_ship1", "description1", 125, "image_url1", "ah"
                    ),
                    PirateShipEntity(
                            1L, "", "description2", 225, null, "ay"
                    ),
                    PirateShipEntity(
                            2L, "pirate_ship3", "description3", 325, "image_url3", "ar"
                    ),
                    PirateShipEntity(
                            3L, "pirate_ship4", "description4", 425, "image_url4", "yo"
                    ),
                    PirateShipEntity(
                            4L, "pirate_ship5", "description5", 525, "image_url5", null
                    ),
            )

    val factory = createFactory()

    // when

    val results = factory.fromEntities(dummyEntities)

    // then

    MatcherAssert.assertThat(results.size, `is`(5))
    MatcherAssert.assertThat(
            results[0],
            `is`(PirateShip(0L, "pirate_ship1", "description1", "125", "image_url1", "Ahoi!"))
    )
    MatcherAssert.assertThat(
            results[1],
            `is`(PirateShip(1L, "", "description2", "225", null, "Aye Aye!"))
    )
    MatcherAssert.assertThat(
            results[2],
            `is`(PirateShip(2L, "pirate_ship3", "description3", "325", "image_url3", "Arrr!"))
    )
    MatcherAssert.assertThat(
            results[3],
            `is`(PirateShip(3L, "pirate_ship4", "description4", "425", "image_url4", "Yo ho hooo!"))
    )
    MatcherAssert.assertThat(
            results[4],
            `is`(PirateShip(4L, "pirate_ship5", "description5", "525", "image_url5", "Ahoi!"))
    )
  }

  override fun createFactory() = PirateShipFactory()
}
