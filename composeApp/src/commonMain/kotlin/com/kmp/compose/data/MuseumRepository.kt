package com.kmp.compose.data

import com.kmp.compose.data.HttpClientSingleton.client
import com.kmp.compose.utils.TimeUtil
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.absolutePath
import io.github.vinceglb.filekit.cacheDir
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class MuseumRepository private constructor(
    private val museumApi: MuseumApi,
    private val museumStorage: MuseumStorage,
) {

    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        museumStorage.saveObjects(museumApi.getData())
    }

    fun getObjects(): Flow<List<Museum>> = museumStorage.getObjects()

    fun getObjectById(objectId: Long): Flow<Museum?> = museumStorage.getObjectById(objectId)


    @OptIn(ExperimentalTime::class)
    suspend fun downloadFile(url: String) = withContext(Dispatchers.IO) {
        val formattedDateTime = TimeUtil.formatTimeNow()
        val file = PlatformFile(FileKit.cacheDir.absolutePath() +  "/image-$formattedDateTime.jpeg")
        val bytes = client.get(url).readRawBytes()
        FileSystem.SYSTEM.write(file.absolutePath().toPath()) {
            write(bytes)
        }
        file.absolutePath()
    }

    companion object {
        val INSTANCE: MuseumRepository = lazy {
            val api = KtorMuseumApi(client)
            val storage = InMemoryMuseumStorage()
            MuseumRepository(api, storage).also {
                it.initialize()
            }
        }.value
    }
}
