package bilek.pirateships.factory

abstract class BaseFactoryTest<T : Any> {

  protected abstract fun createFactory(): T
}
