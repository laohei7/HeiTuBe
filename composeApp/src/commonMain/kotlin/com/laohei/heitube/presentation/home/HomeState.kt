package com.laohei.heitube.presentation.home

import com.laohei.heitube.core.presentation.UiText
import com.laohei.heitube.domain.VideoItem

data class HomeState(
    val isLoading: Boolean = true,
    val errorMessage: UiText? = null,
    val videos: List<VideoItem> = emptyList()
)