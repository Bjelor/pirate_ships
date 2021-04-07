package bilek.pirateships.core

import android.app.Application

class PirateShipsApplication : Application() {

    lateinit var dependencyContainer: DependencyContainer
        private set

    override fun onCreate() {
        super.onCreate()

        dependencyContainer = DependencyContainer(this)
    }
}
