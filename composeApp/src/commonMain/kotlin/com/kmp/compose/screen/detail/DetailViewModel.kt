package com.kmp.compose.screen.detail

import androidx.lifecycle.ViewModel
import com.kmp.compose.data.Museum
import com.kmp.compose.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailViewModel() : ViewModel() {
    private val museumRepository: MuseumRepository = MuseumRepository.INSTANCE
    fun getObject(objectId: Long): Flow<Museum?> =
        museumRepository.getObjectById(objectId)
}
