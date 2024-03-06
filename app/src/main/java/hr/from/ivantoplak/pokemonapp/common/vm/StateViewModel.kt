package hr.from.ivantoplak.pokemonapp.common.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Abstract class that models states, events and actions for all ViewModels.
 * Each ViewModel should follow this pattern to increase code consistency and reduce repetition.
 * NOTE: Don't modify this class by adding more functionality, this one is just for state management.
 *
 *
 * S = State from VM to UI, represents the current data for view to render
 *
 * E = Event form VM to UI, not related with the UI state (effects)
 *
 * A = Action represent user interaction (event from UI to VM)
 */

abstract class StateViewModel<S : Any, E : Any, A : Any>(
    initialState: S,
    initialEvent: E,
) : ViewModel() {
    // UI state
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    // Events
    private val _event = MutableStateFlow(initialEvent)
    val event: StateFlow<E> = _event.asStateFlow()

    /**
     * Sets the state object as current state.
     */
    fun S.sendToState() {
        _state.value = this
    }

    /**
     * Sets the event object as current event.
     */
    fun E.sendToEvent() {
        _event.value = this
    }

    /**
     * Reduces the action to the new state.
     */
    abstract fun reduce(action: A)
}
