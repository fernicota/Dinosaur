package com.example.dinosaur.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinosaur.network.DinosaurApi
import com.example.dinosaur.network.DinosaurPhoto
import kotlinx.coroutines.launch

sealed interface DinosaursUiState {
    data class Success(val photos: List<DinosaurPhoto>) : DinosaursUiState
    object Error : DinosaursUiState
    object Loading : DinosaursUiState
}

class DinosaurViewModel : ViewModel() {
    var dinosaursUiState: DinosaursUiState by mutableStateOf(DinosaursUiState.Loading)
        private set

    init {
        getDinosaurPhotos()
    }

    fun getDinosaurPhotos() {

        viewModelScope.launch {
            dinosaursUiState = try {
                DinosaursUiState.Success(
                    DinosaurApi.retrofitService.getPhotos()
                )

            } catch (e: Exception) {
                DinosaursUiState.Error
            }

        }
    }
}