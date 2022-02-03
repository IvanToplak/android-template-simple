package hr.from.ivantoplak.pokemonapp.extensions

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import hr.from.ivantoplak.pokemonapp.viewmodel.event.Event
import hr.from.ivantoplak.pokemonapp.viewmodel.event.EventObserver

fun <T> MutableLiveData<Event<T>>.postEvent(event: T) {
    postValue(Event(event))
}

fun <T> MutableLiveData<Event<T>>.setEvent(event: T) {
    value = Event(event)
}

@MainThread
inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<Event<T>> {
    val wrappedObserver = EventObserver<T> { value ->
        onChanged.invoke(value)
    }
    observe(owner, wrappedObserver)
    return wrappedObserver
}
