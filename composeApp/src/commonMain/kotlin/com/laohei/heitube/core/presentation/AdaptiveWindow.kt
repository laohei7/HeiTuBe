package com.laohei.heitube.core.presentation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AdaptiveWindow {
    Small, Middle, Large;

    companion object {
        fun adaptive(maxWidth: Dp): AdaptiveWindow {
            return if (maxWidth <= 800.dp) Small
            else if (maxWidth > 800.dp && maxWidth <= 1300.dp) Middle
            else Large
        }
    }
}
