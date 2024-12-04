package com.laohei.heitube.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.laohei.heitube.core.extension.formatTimeString
import com.laohei.heitube.core.extension.toTimeAgoString
import com.laohei.heitube.core.extension.toViewString
import com.laohei.heitube.domain.VideoInfo
import com.laohei.heitube.domain.VideoItem
import com.laohei.heitube.presentation.component.Divider
import com.laohei.heitube.presentation.component.MenuItem
import heitube.composeapp.generated.resources.*
import heitube.composeapp.generated.resources.Res
import heitube.composeapp.generated.resources.icon_playlist
import heitube.composeapp.generated.resources.icon_watch_later
import heitube.composeapp.generated.resources.str_view_and_time
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

internal fun LazyGridState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}


private val filters = listOf(
    Res.string.str_all,
    Res.string.str_game,
    Res.string.str_live,
    Res.string.str_cartoon,
    Res.string.str_upload_latest,
    Res.string.str_watched,
    Res.string.str_published_video,
)

private val menus = listOf(
    Pair(Res.drawable.icon_playlist, Res.string.str_add_to_playlist),
    Pair(Res.drawable.icon_watch_later, Res.string.str_save_wathc_later),
    Pair(Res.drawable.icon_label, Res.string.str_save_to_playlist),
    Pair(Res.drawable.ico_download, Res.string.str_download),
    Pair(Res.drawable.icon_share, Res.string.str_shared),
    Pair(Res.drawable.icon_forbidden, Res.string.str_no_intresting),
    Pair(Res.drawable.icon_forbidden_horizontal, Res.string.str_no_recommendation),
    Pair(Res.drawable.icon_inform, Res.string.str_infom),
)

@OptIn(ExperimentalMaterialApi::class)
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

    var filter by remember { mutableStateOf(filters.first()) }

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
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filters) {
                    Chip(
                        onClick = { filter = it },
                        shape = RoundedCornerShape(10.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (filter == it) Color.Black
                            else MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                                .compositeOver(MaterialTheme.colors.surface),
                            contentColor = if (filter == it) Color.White else Color.Black
                        )
                    ) {
                        Text(text = stringResource(it))
                    }
                }
            }
        }
        //                                item { Text(text = maxWidth.toString()) }
        items(uiState.videos) { video ->
            VideoCard(
                videoItem = video, videoInfo = uiState.videoInfo,
                isPreview = uiState.previewAid == video.aid && uiState.videoInfo != null
            ) {
                println(video.title)
                scope.launch {
                    homeViewModel.getVideo(video)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun VideoCard(
    videoItem: VideoItem,
    videoInfo: VideoInfo?,
    isPreview: Boolean,
    onClick: (action: HomeAction) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick(HomeAction.VideoAction)
            }
            .pointerHoverIcon(PointerIcon.Hand),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        BoxWithConstraints {
            val dynamicHeight = maxWidth * (9f / 16f)
            Box(
                modifier = Modifier.fillMaxWidth().height(dynamicHeight)
                    .clip(RoundedCornerShape(10.dp)),
            ) {
                AsyncImage(
                    model = videoItem.pic,
                    contentDescription = videoItem.bvid,
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillBounds,
                )

                Text(
                    text = videoItem.duration.formatTimeString(),
                    style = MaterialTheme.typography.caption,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(end = 10.dp, bottom = 10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(5.dp)
                )
            }
        }




        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(end = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AsyncImage(
                    model = videoItem.owner.face,
                    contentDescription = videoItem.owner.name,
                    modifier = Modifier.size(36.dp).clip(CircleShape),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = videoItem.title,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = videoItem.owner.name,
                        style = MaterialTheme.typography.subtitle2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )

                    Text(
                        text = stringResource(
                            Res.string.str_view_and_time,
                            videoItem.stat.view.toViewString(),
                            videoItem.ctime.toTimeAgoString()
                        ),
                        style = MaterialTheme.typography.subtitle2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )
                }

            }

            ExposedDropdownMenuBox(
                modifier = Modifier.align(Alignment.TopEnd),
                expanded = isExpanded,
                onExpandedChange = {}
            ) {
                IconButton(onClick = { isExpanded = !isExpanded }, modifier = Modifier.align(Alignment.TopEnd)) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.Black)
                }
                ExposedDropdownMenu(
                    modifier = Modifier.width(220.dp).padding(vertical = 8.dp),
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    menus.fastForEachIndexed { index, item ->
                        DropdownMenuItem(
                            onClick = {}
                        ) {
                            MenuItem(
                                label = stringResource(item.second),
                                icon = item.first,
                            )
                        }
                        if (index == 4) {
                            Divider(paddingValues = PaddingValues(horizontal = 0.dp, vertical = 10.dp))
                        }
                    }
                }
            }


        }

    }
}


