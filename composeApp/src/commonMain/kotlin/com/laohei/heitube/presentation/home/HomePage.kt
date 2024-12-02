package com.laohei.heitube.presentation.home

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch

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
                    model = video.pic,
                    contentDescription = video.bvid,
                    modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(10.dp)),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillBounds,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AsyncImage(
                        model = video.owner.face,
                        contentDescription =video.owner.name,
                        modifier = Modifier.size(36.dp).clip(CircleShape),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit,
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


