package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.pokemon.mappings.toUIMove
import hr.from.ivantoplak.pokemonapp.pokemon.model.Move
import hr.from.ivantoplak.pokemonapp.pokemon.model.PokemonRepository
import hr.from.ivantoplak.pokemonapp.pokemon.vm.MovesState
import hr.from.ivantoplak.pokemonapp.pokemon.vm.MovesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovesViewModelTest {

    // bypasses the main thread check, and immediately runs any tasks on your test thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testCoroutineScope = TestScope(testDispatcher)
    private val dispatcher = mock<DispatcherProvider>()
    private val repo = mock<PokemonRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun shouldGetListOfMoves() = testCoroutineScope.runTest {
        // arrange
        val pokemonId = 1
        val pokemonName = "facepalm"
        val moves = listOf(Move(pokemonId, pokemonName))
        whenever(repo.getPokemonMoves(pokemonId)).thenReturn(flow { emit(moves) })
        whenever(dispatcher.io()).thenReturn(testDispatcher)

        // act
        val viewModel = MovesViewModel(pokemonId, repo, dispatcher)

        // assert
        verify(dispatcher).io()
        verify(repo).getPokemonMoves(pokemonId)
        val expected = moves.map { it.toUIMove() }
        assertTrue(viewModel.state.value is MovesState.Ready)
        assertEquals(expected, (viewModel.state.value as MovesState.Ready).moves)
    }

    @Test
    fun shouldGetEmptyListWhenExceptionThrown() = testCoroutineScope.runTest {
        // arrange
        val pokemonId = 0
        whenever(repo.getPokemonMoves(pokemonId)).thenReturn(flow { throw (Throwable()) })
        whenever(dispatcher.io()).thenReturn(testDispatcher)

        // act
        val viewModel = MovesViewModel(pokemonId, repo, dispatcher)

        // assert
        verify(dispatcher).io()
        verify(repo).getPokemonMoves(pokemonId)
        assertTrue(viewModel.state.value is MovesState.Error)
    }
}
