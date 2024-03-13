package hr.from.ivantoplak.pokemonapp.common.localstore

import kotlinx.coroutines.flow.Flow

/**
 * LocalStore is responsible for storing and retrieving data from the local storage.
 * Data is stored in a key-value pair format.
 */
interface LocalStore {

    companion object Key {
        const val AuthToken = "KEY_AUTH_TOKEN"
    }

    fun getStringValueFlow(key: String): Flow<String>

    suspend fun getStringValue(key: String): String

    suspend fun setStringValue(key: String, value: String)

    fun getBooleanValue(key: String): Flow<Boolean>

    suspend fun setBooleanValue(key: String, value: Boolean)

    fun getIntValue(key: String): Flow<Int>

    suspend fun setIntValue(key: String, value: Int)

    fun getLongValue(key: String): Flow<Long>

    suspend fun setLongValue(key: String, value: Long)

    fun getFloatValue(key: String): Flow<Float>

    suspend fun setFloatValue(key: String, value: Float)

    fun getDoubleValue(key: String): Flow<Double>

    suspend fun setDoubleValue(key: String, value: Double)

    fun getStringSetValue(key: String): Flow<Set<String>>

    suspend fun setStringSetValue(key: String, value: Set<String>)
}
