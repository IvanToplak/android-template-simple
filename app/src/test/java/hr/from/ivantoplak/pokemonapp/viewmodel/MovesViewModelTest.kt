package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.mappings.toMoveViewData
import hr.from.ivantoplak.pokemonapp.model.Move
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
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

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)
    private val dispatcher = mock<DispatcherProvider>()
    private val repo = mock<PokemonRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun shouldGetListOfMoves() = testCoroutineScope.runBlockingTest {
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
        val expected = moves.map { it.toMoveViewData() }
        assertEquals(expected, viewModel.moves.value)
    }

    @Test
    fun shouldGetEmptyListWhenExceptionThrown() = testCoroutineScope.runBlockingTest {
        // arrange
        val pokemonId = 0
        whenever(repo.getPokemonMoves(pokemonId)).thenReturn(flow { throw (Throwable()) })
        whenever(dispatcher.io()).thenReturn(testDispatcher)

        // act
        val viewModel = MovesViewModel(pokemonId, repo, dispatcher)

        // assert
        verify(dispatcher).io()
        verify(repo).getPokemonMoves(pokemonId)
        assertTrue(viewModel.moves.value.isNullOrEmpty())
    }
}
