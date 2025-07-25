package com.kmp.compose.screen.detail

import androidx.lifecycle.ViewModel
import com.kmp.compose.data.MuseumObject
import com.kmp.compose.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailViewModel() : ViewModel() {
    private val museumRepository: MuseumRepository = MuseumRepository.INSTANCE
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
