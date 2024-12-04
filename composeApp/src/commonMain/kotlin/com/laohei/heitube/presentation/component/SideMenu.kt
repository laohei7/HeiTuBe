package com.laohei.heitube.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import heitube.composeapp.generated.resources.*
import heitube.composeapp.generated.resources.Res
import heitube.composeapp.generated.resources.icon_home
import heitube.composeapp.generated.resources.icon_shorts
import heitube.composeapp.generated.resources.icon_subscribe
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

private val rails = listOf(
    Pair(Res.drawable.icon_home, "Home"),
    Pair(Res.drawable.icon_shorts, "Shorts"),
    Pair(Res.drawable.icon_subscribe, "Subscribe"),
    Pair(Res.drawable.icon_profile, "Me"),
)

private val menus = listOf(
    Pair(Res.drawable.icon_home, "Home"),
    Pair(Res.drawable.icon_shorts, "Shorts"),
    Pair(Res.drawable.icon_subscribe, "Subscribe"),
)

private val myMenus = listOf(
    Pair(Res.drawable.icon_history, "History"),
    Pair(Res.drawable.icon_playlist, "Playlist"),
    Pair(Res.drawable.icon_my_video, "My Video"),
    Pair(Res.drawable.icon_watch_later, "Watch Later"),
    Pair(Res.drawable.icon_praise, "Praise"),
)

private val otherMenus = listOf(
    Pair(Res.drawable.icon_setting, "Setting"),
    Pair(Res.drawable.icon_inform, "Inform"),
    Pair(Res.drawable.icon_assist, "Assist"),
    Pair(Res.drawable.icon_feedback, "Feedback"),
)

@Composable
fun SideMenuRail() {
    NavigationRail(
        elevation = 0.dp
    ) {
        rails.fastForEach {
            NavigationRailItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(it.first), contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = { Text(text = it.second, style = MaterialTheme.typography.caption) },
                unselectedContentColor = Color.Black
            )
        }
    }
}

@Composable
fun SideMenuList(
    paddingValues: PaddingValues = PaddingValues()
) {
    var select by remember { mutableStateOf(Res.drawable.icon_home) }
    LazyColumn(
        modifier = Modifier.width(260.dp).padding(paddingValues)
    ) {
        items(menus) {
            SideMenuItem(
                label = it.second,
                icon = it.first,
                selected = select == it.first,
                onClick = { select = it.first }
            )
        }
        item { Divider() }

        item {
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { select = Res.drawable.icon_arrow_right }
                    .background(
                        if (select == Res.drawable.icon_arrow_right) Color.LightGray.copy(alpha = 0.5f) else Color.White
                    )
                    .padding(horizontal = 20.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "Me", style = MaterialTheme.typography.subtitle1)
                Icon(
                    painter = painterResource(Res.drawable.icon_arrow_right), contentDescription = null,
                    modifier = Modifier.size(12.dp)
                )
            }
        }

        items(myMenus) {
            SideMenuItem(
                label = it.second,
                icon = it.first,
                selected = select == it.first,
                onClick = { select = it.first }
            )
        }

        item { Divider() }
        item { GroupTitle("Subscribe") }

        item { Divider() }
        item { GroupTitle("Explore") }

        item { Divider() }

        items(otherMenus) {
            SideMenuItem(
                label = it.second,
                icon = it.first,
                selected = select == it.first,
                onClick = { select = it.first }
            )
        }
        item { Divider() }

    }
}

@Composable
private fun GroupTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    )
}

@Composable
private fun SideMenuItem(
    label: String,
    icon: DrawableResource,
    selected: Boolean,
    onClick: () -> Unit
) {
    MenuItem(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .background(
                if (selected) Color.LightGray.copy(alpha = 0.5f) else Color.White
            )
            .padding(horizontal = 20.dp, vertical = 5.dp),
        icon = icon,
        label = label,
    )
}

@Composable
fun Divider(
    paddingValues: PaddingValues = PaddingValues(horizontal = 15.dp, vertical = 10.dp)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(22.dp)
            .padding(paddingValues)
            .background(color = Color.LightGray.copy(alpha = 0.5f))
            .clip(CircleShape)
    )
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    label: String,
    tail: DrawableResource? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(icon), contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Text(text = label, style = MaterialTheme.typography.subtitle1)
        }
        tail?.let {
            Icon(
                painter = painterResource(it), contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
        }

    }
}