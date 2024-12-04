package com.laohei.heitube.presentation.home

sealed interface HomeAction {
    data object VideoAction : HomeAction
    data object VideoMoreAction : HomeAction
}
