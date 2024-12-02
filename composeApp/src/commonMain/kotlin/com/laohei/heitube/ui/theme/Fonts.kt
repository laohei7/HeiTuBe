package com.laohei.heitube.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import heitube.composeapp.generated.resources.Res
import heitube.composeapp.generated.resources.noto_sans_sc_bold
import heitube.composeapp.generated.resources.noto_sans_sc_regular
import org.jetbrains.compose.resources.Font


@Composable
fun FontSans() = FontFamily(
    Font(Res.font.noto_sans_sc_regular),
    Font(Res.font.noto_sans_sc_bold, weight = FontWeight.Bold),
)