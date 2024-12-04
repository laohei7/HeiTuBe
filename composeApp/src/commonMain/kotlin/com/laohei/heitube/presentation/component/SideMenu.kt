package com.laohei.heitube.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.laohei.heitube.Screen
import heitube.composeapp.generated.resources.*
import heitube.composeapp.generated.resources.Res
import heitube.composeapp.generated.resources.icon_home
import heitube.composeapp.generated.resources.icon_shorts
import heitube.composeapp.generated.resources.icon_subscribe
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val rails = listOf(
    Pair(Res.drawable.icon_home, Res.string.str_home),
    Pair(Res.drawable.icon_shorts, Res.string.str_shorts),
    Pair(Res.drawable.icon_subscribe, Res.string.str_subscribe),
    Pair(Res.drawable.icon_profile, Res.string.str_mine),
)

private val menus = listOf(
    Pair(Res.drawable.icon_home, Res.string.str_home),
    Pair(Res.drawable.icon_shorts, Res.string.str_shorts),
    Pair(Res.drawable.icon_subscribe, Res.string.str_subscribe),
)

private val myMenus = listOf(
    Pair(Res.drawable.icon_history, Res.string.str_history),
    Pair(Res.drawable.icon_playlist, Res.string.str_playlist),
    Pair(Res.drawable.icon_my_video, Res.string.str_my_video),
    Pair(Res.drawable.icon_watch_later, Res.string.str_watch_later),
    Pair(Res.drawable.icon_praise, Res.string.str_praise),
)

private val exploreMenus = listOf(
    Pair(Res.drawable.icon_hots, Res.string.str_hots),
    Pair(Res.drawable.icon_music, Res.string.str_music),
    Pair(Res.drawable.icon_moive, Res.string.str_moive),
    Pair(Res.drawable.icon_live, Res.string.str_live),
    Pair(Res.drawable.icon_game, Res.string.str_game),
    Pair(Res.drawable.icon_news, Res.string.str_news),
    Pair(Res.drawable.icon_study, Res.string.str_study),
    Pair(Res.drawable.icon_fashion, Res.string.str_fashion),
    Pair(Res.drawable.icon_podcast, Res.string.str_podcast),
    Pair(Res.drawable.icon_game_center, Res.string.str_game_center),
)

private val subscriptions = listOf(
    Triple(Res.drawable.user_1, "甜甜讲动漫", true),
    Triple(Res.drawable.user_2, "呆呆说动漫", true),
    Triple(Res.drawable.user_3, "Philipp Lackner", false),
    Triple(Res.drawable.user_4, "Android Developers", true),
    Triple(Res.drawable.user_5, "Stevdza-San", true),
    Triple(Res.drawable.user_6, "魔人小白", false),
    Triple(Res.drawable.user_7, "星芒漫画", false),
    Triple(Res.drawable.user_8, "HaneAme雨波", true),
    Triple(Res.drawable.user_9, "MoomoRina", false),
    Triple(Res.drawable.user_10, "Fate/Grand Order", true),
)

private val otherMenus = listOf(
    Pair(Res.drawable.icon_setting, Res.string.str_setting),
    Pair(Res.drawable.icon_inform, Res.string.str_inform_record),
    Pair(Res.drawable.icon_assist, Res.string.str_assist),
    Pair(Res.drawable.icon_feedback, Res.string.str_feedback),
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
                label = { Text(text = stringResource(it.second), style = MaterialTheme.typography.caption) },
                unselectedContentColor = Color.Black
            )
        }
    }
}

@Composable
fun SideMenuList(
    paddingValues: PaddingValues = PaddingValues(),
    navigateToRoute: (screen: Screen) -> Unit
) {
    var select by remember { mutableStateOf(Res.drawable.icon_home) }
    var isExpanded by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.width(260.dp).padding(paddingValues)
    ) {
        items(menus) {
            SideMenuItem(
                label = stringResource(it.second),
                icon = it.first,
                selected = select == it.first,
                onClick = {
                    select = it.first
                    navigateToRoute(Screen.Home)
                }
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
                Text(text = stringResource(Res.string.str_me), style = MaterialTheme.typography.subtitle1)
                Icon(
                    painter = painterResource(Res.drawable.icon_arrow_right), contentDescription = null,
                    modifier = Modifier.size(12.dp)
                )
            }
        }

        items(myMenus) {
            SideMenuItem(
                label = stringResource(it.second),
                icon = it.first,
                selected = select == it.first,
                onClick = { select = it.first }
            )
        }

        item { Divider() }
        item { GroupTitle(stringResource(Res.string.str_subscribe)) }
        itemsIndexed(
            if (isExpanded) subscriptions else if (subscriptions.size <= 7) subscriptions
            else subscriptions.subList(0, 7)
        ) { index, it ->
            UserMenuItem(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        select = it.first
                        navigateToRoute(Screen.Subscription(it.second, "user_${index + 1}"))
                    }
                    .background(
                        if (select == it.first) Color.LightGray.copy(alpha = 0.5f) else Color.White
                    )
                    .padding(horizontal = 20.dp, vertical = 5.dp),
                icon = it.first,
                label = it.second,
                showIndicator = it.third
            )
        }

        if (isExpanded) {
            item {
                SideMenuItem(
                    label = stringResource(Res.string.str_all_subscribe),
                    icon = Res.drawable.icon_all_subcribe,
                    selected = select == Res.drawable.icon_all_subcribe,
                    onClick = { select = Res.drawable.icon_all_subcribe }
                )
            }
        }
        item {
            SideMenuItem(
                label = stringResource(if (isExpanded) Res.string.str_hide else Res.string.str_expand),
                icon = if (isExpanded) Res.drawable.icon_arrow_up else Res.drawable.icon_arrow_down,
                selected = false,
                onClick = { isExpanded = !isExpanded }
            )
        }


        item { Divider() }
        item { GroupTitle(stringResource(Res.string.str_explore)) }
        items(exploreMenus) {
            SideMenuItem(
                label = stringResource(it.second),
                icon = it.first,
                selected = select == it.first,
                onClick = { select = it.first }
            )
        }

        item { Divider() }

        items(otherMenus) {
            SideMenuItem(
                label = stringResource(it.second),
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
private fun UserMenuItem(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    label: String,
    showIndicator: Boolean
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
            Image(
                painter = painterResource(icon), contentDescription = null,
                modifier = Modifier.size(22.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = label,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (showIndicator) {
            Box(
                modifier = Modifier.padding(start = 5.dp).size(5.dp).clip(CircleShape).background(Color.Blue)
            )
        }

    }
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