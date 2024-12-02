package com.laohei.heitube.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laohei.heitube.Platform
import com.laohei.heitube.PlatformType
import com.laohei.heitube.core.data.safeCall
import com.laohei.heitube.core.domain.onError
import com.laohei.heitube.core.domain.onSuccess
import com.laohei.heitube.core.presentation.toUiText
import com.laohei.heitube.di.AppModule
import com.laohei.heitube.domain.ApiResponse
import com.laohei.heitube.getPlatform
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class HomeViewModel : ViewModel() {

    private val host = if (getPlatform().type == PlatformType.Android) "192.168.213.193" else "localhost"

    private var pn = 1
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState
        .onStart {
            getVideoList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    suspend fun getVideoList() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        safeCall<ApiResponse> {
            AppModule.client.get("http://$host:8081/proxy?pn=$pn")
        }.onSuccess { success ->
            val videos = success.data.list.map { video ->
                video.copy(
                    pic = URLBuilder(
                        buildString {
                            append("http://$host:8081/proxy-image?")
                            append("url=").append(video.pic)
                        }
                    ).toString(),
                    owner = video.owner.copy(
                        face = URLBuilder(
                            buildString {
                                append("http://$host:8081/proxy-image?")
                                append("url=").append(video.owner.face)
                            }
                        ).toString()
                    )
                )
            }
            delay(500L)
            pn++
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = null,
                    videos = it.videos.toMutableList().apply { addAll(videos) }.toList()
                )
            }
        }.onError { error ->
            delay(500L)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = error.toUiText()
                )
            }
        }
    }

}