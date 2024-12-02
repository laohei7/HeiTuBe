package com.laohei.heitube.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import heitube.composeapp.generated.resources.Res
import heitube.composeapp.generated.resources.icon_assist
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

internal fun LazyGridState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}

@Composable
fun HomePage(
    count: Int,
    homeViewModel: HomeViewModel = viewModel { HomeViewModel() }
) {
    val uiState by homeViewModel.uiState.collectAsState()

    val lazyGridState = rememberLazyGridState()

    val reachedBottom by remember {
        derivedStateOf { lazyGridState.reachedBottom() }
    }

    val scope = rememberCoroutineScope()

    if (reachedBottom && !uiState.isLoading && uiState.videos.isNotEmpty()) {
        scope.launch {
            homeViewModel.getVideoList()

        }
    }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(count),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(start = 20.dp, end = 30.dp)
    ) {
        item(span = { GridItemSpan(count) }) {

        }
        //                                item { Text(text = maxWidth.toString()) }
        items(uiState.videos) { video ->
            Column(
                modifier = Modifier.width(width = 260.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(10.dp)),
                    url = video.pic,
                    label = video.bvid
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.size(36.dp).clip(CircleShape),
                        url = video.owner.face,
                        label = video.owner.name
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = video.title,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = video.owner.name,
                            style = MaterialTheme.typography.subtitle2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                        Text(
                            text = video.stat.view.toString(),
                            style = MaterialTheme.typography.subtitle2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.Black)
                    }
                }
            }
        }

        item(span = { GridItemSpan(count) }) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun AsyncImage(
    modifier: Modifier = Modifier,
    url: String,
    label: String
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }

    val painter = rememberAsyncImagePainter(
        model = url,
        onSuccess = {
            imageLoadResult =
                if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                    Result.success(it.painter)
                } else {
                    Result.failure(Exception("Invalid image size"))
                }
        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )
    when (val result = imageLoadResult) {
        null -> {}
        else -> {
            Image(
                painter = if (result.isSuccess) painter else {
                    painterResource(Res.drawable.icon_assist)
                },
                contentDescription = label,
                contentScale = if (result.isSuccess) {
                    ContentScale.Crop
                } else {
                    ContentScale.Fit
                },
                modifier = modifier
            )
        }
    }
}
