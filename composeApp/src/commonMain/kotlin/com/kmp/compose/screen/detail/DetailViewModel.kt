package com.kmp.compose.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.compose.data.Museum
import com.kmp.compose.data.MuseumRepository
import com.kmp.compose.utils.Log
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.absolutePath
import io.github.vinceglb.filekit.cacheDir
import io.github.vinceglb.filekit.saveImageToGallery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.SYSTEM

class DetailViewModel() : ViewModel() {
    private val museumRepository: MuseumRepository = MuseumRepository.INSTANCE
    fun getObject(objectId: Long): Flow<Museum?> =
        museumRepository.getObjectById(objectId)

    fun savePhoto(url: String) {
        viewModelScope.launch {
            runCatching {
                museumRepository.downloadFile(url)
            }.onFailure {
                Log.wtf("DetailViewModel", "Error saving photo", it)
            }.onSuccess {
                Log.d("DetailViewModel", "Photo saved")
                FileKit.saveImageToGallery(PlatformFile(it), "photo.jpeg")
            }
        }
    }
}
