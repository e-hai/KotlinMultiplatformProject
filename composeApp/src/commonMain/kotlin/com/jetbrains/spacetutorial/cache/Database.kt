package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.kmp.compose.data.Museum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries


    internal fun getMuseums(): Flow<List<Museum>> {
        return dbQuery.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map { entity ->
                    Museum(
                        objectID = entity.objectID,
                        title = entity.title,
                        artistDisplayName = entity.artistDisplayName,
                        medium = entity.medium,
                        dimensions = entity.dimensions,
                        objectURL = entity.objectURL,
                        objectDate = entity.objectDate,
                        primaryImage = entity.primaryImage,
                        primaryImageSmall = entity.primaryImageSmall,
                        repository = entity.repository,
                        department = entity.department,
                        creditLine = entity.creditLine,
                    )
                }
            }
    }

    internal fun insertMuseums(museums: List<Museum>) {
        museums.forEach {
            dbQuery.insert(
                objectID = it.objectID,
                title = it.title,
                artistDisplayName = it.artistDisplayName,
                medium = it.medium,
                dimensions = it.dimensions,
                objectURL = it.objectURL,
                objectDate = it.objectDate,
                primaryImage = it.primaryImage,
                primaryImageSmall = it.primaryImageSmall,
                repository = it.repository,
                department = it.department,
                creditLine = it.creditLine,
            )
        }
    }

    internal fun clearAndCreateMuseums(museums: List<Museum>) {
        dbQuery.transaction {
            dbQuery.deleteAll()
            insertMuseums(museums)
        }
    }
}