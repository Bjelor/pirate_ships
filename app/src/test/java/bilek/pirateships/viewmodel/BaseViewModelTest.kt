package bilek.pirateships.viewmodel

import androidx.lifecycle.ViewModel
import bilek.pirateships.BaseTest

abstract class BaseViewModelTest<T : ViewModel> : BaseTest() {

  protected abstract fun createViewModel(): T
}
