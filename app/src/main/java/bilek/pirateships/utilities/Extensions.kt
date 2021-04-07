package bilek.pirateships.utilities

import android.app.Activity
import androidx.fragment.app.Fragment
import bilek.pirateships.core.DependencyContainer
import bilek.pirateships.core.PirateShipsApplication

val Activity.app: PirateShipsApplication
    get() = application as PirateShipsApplication

val Activity.dependencyContainer: DependencyContainer
    get() = app.dependencyContainer

val Fragment.dependencyContainer: DependencyContainer
    get() = requireActivity().app.dependencyContainer