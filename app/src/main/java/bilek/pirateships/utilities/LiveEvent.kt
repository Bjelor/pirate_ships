package bilek.pirateships.utilities

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Source: https://www.droidcon.com/news-detail?content-id=/repository/collaboration/Groups/spaces/droidcon_hq/Documents/public/news/android-news/Navigation%20and%20Events%20in%20MVVM%20on%20Android%20via%20LiveData
 *
 */
open class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Observe the internal MutableLiveData
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Util function for Void implementations.
     */
    fun call() {
        value = null
    }
}