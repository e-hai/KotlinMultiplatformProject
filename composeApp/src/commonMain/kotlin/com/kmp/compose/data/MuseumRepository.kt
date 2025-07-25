package com.kmp.compose.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MuseumRepository private constructor(
    private val museumApi: MuseumApi,
    private val museumStorage: MuseumStorage,
) {

    companion object {
        val INSTANCE: MuseumRepository = lazy {
            val client =HttpClient {
                install(ContentNegotiation) {
                    // TODO Fix API so it serves application/json
                    json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
                }
            }
            val api = KtorMuseumApi(client)
            val storage = InMemoryMuseumStorage()
            MuseumRepository(api, storage).also {
                it.initialize()
            }
        }.value
    }

    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        museumStorage.saveObjects(museumApi.getData())
    }

    fun getObjects(): Flow<List<MuseumObject>> = museumStorage.getObjects()

    fun getObjectById(objectId: Int): Flow<MuseumObject?> = museumStorage.getObjectById(objectId)
}
