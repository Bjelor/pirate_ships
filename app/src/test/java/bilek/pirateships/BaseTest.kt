package bilek.pirateships

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class BaseTest {

  @JvmField
  @Rule
  val rule = InstantTaskExecutorRule()

  protected val testCoroutineDispatcher = TestCoroutineDispatcher()

  @CallSuper
  @Before
  open fun setUp() {
    Dispatchers.setMain(testCoroutineDispatcher)
  }

  @CallSuper
  @After
  open fun tearDown() {
    Dispatchers.resetMain()
    testCoroutineDispatcher.cleanupTestCoroutines()
  }

  protected fun runBlockingTest(
          testBody: suspend TestCoroutineScope.() -> Unit,
  ) = kotlinx.coroutines.test.runBlockingTest(testCoroutineDispatcher, testBody)

  protected inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)

  protected inline fun <reified T> any(): T = org.mockito.kotlin.any()

  protected fun verifyZeroInteractions(mock: Any) = org.mockito.kotlin.verifyZeroInteractions(mock)
}