package com.laohei.heitube.presentation.subscription

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.laohei.heitube.Screen
import heitube.composeapp.generated.resources.Res
import heitube.composeapp.generated.resources.allDrawableResources
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SubscriptionPage(
    subscription: Screen.Subscription
) {
    println(subscription)
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 30.dp).fillMaxSize()
    ) {
        Row {
            Image(
                painter = painterResource(Res.allDrawableResources[subscription.icon]!!),
                contentDescription = null,
                modifier = Modifier.size(150.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(text = subscription.name, style = MaterialTheme.typography.h6)
        }
    }
}