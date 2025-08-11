package com.kmp.compose.data

import com.jetbrains.spacetutorial.cache.Database
import com.jetbrains.spacetutorial.cache.getDatabaseDriverFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface MuseumStorage {
    suspend fun saveObjects(newObjects: List<Museum>)

    fun getObjectById(objectId: Long): Flow<Museum?>

    fun getObjects(): Flow<List<Museum>>
}

class InMemoryMuseumStorage() : MuseumStorage {
    private val db: Database = Database(getDatabaseDriverFactory())


    override suspend fun saveObjects(newObjects: List<Museum>) {
        db.clearAndCreateMuseums(newObjects)
    }

    override fun getObjectById(objectId: Long): Flow<Museum?> {
        return db.getMuseums().map {
            it.firstOrNull { museum ->
                museum.objectID == objectId
            }
        }
    }

    override fun getObjects(): Flow<List<Museum>> = db.getMuseums()

}
