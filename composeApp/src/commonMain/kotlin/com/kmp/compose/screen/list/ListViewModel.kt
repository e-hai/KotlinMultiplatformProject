package com.kmp.compose.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.compose.data.MuseumObject
import com.kmp.compose.data.MuseumRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListViewModel() : ViewModel() {
    private val museumRepository: MuseumRepository = MuseumRepository.INSTANCE

    val objects: StateFlow<List<MuseumObject>> =
        museumRepository.getObjects()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
