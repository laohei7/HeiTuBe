package com.laohei.heitube.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import heitube.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

sealed class TuBeTopBarAction {
    data object ExpandAction : TuBeTopBarAction()
}


private val userMenus = listOf(
    Triple(Res.drawable.icon_google, Res.string.str_google_account, null),
    Triple(Res.drawable.icon_account_switch, Res.string.str_account_switch, Res.drawable.icon_arrow_right),
    Triple(Res.drawable.icon_exit, Res.string.str_exit_switch, null),
    Triple(Res.drawable.icon_workspace, Res.string.str_workspace, null),
    Triple(Res.drawable.icon_coin, Res.string.str_member, null),
    Triple(Res.drawable.icon_personal_data, Res.string.str_personal_data, null),
    Triple(Res.drawable.icon_skin, Res.string.str_skin, Res.drawable.icon_arrow_right),
    Triple(Res.drawable.icon_language, Res.string.str_language, Res.drawable.icon_arrow_right),
    Triple(Res.drawable.icon_limited, Res.string.str_limited_mode, Res.drawable.icon_arrow_right),
    Triple(Res.drawable.icon_location, Res.string.str_location, Res.drawable.icon_arrow_right),
    Triple(Res.drawable.icon_keyboard, Res.string.str_keyboard, null),
    Triple(Res.drawable.icon_setting, Res.string.str_setting, null),
    Triple(Res.drawable.icon_assist, Res.string.str_assist, null),
    Triple(Res.drawable.icon_feedback, Res.string.str_feedback, null),
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TuBeTopBar(
    showSearchField: Boolean,
    onClick: (action: TuBeTopBarAction) -> Unit
) {
    var searchValue by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    IconButton(onClick = { onClick(TuBeTopBarAction.ExpandAction) }) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                    Image(
                        painter = painterResource(Res.drawable.icon_youtube),
                        contentDescription = null,
                        modifier = Modifier.scale(0.5f)
                    )
                }

                if (showSearchField) {
                    var hasFocus by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .sizeIn(minWidth = 120.dp)
                                .fillMaxWidth(0.4f)
                                .background(Color.White, shape = CircleShape)
                                .border(1.dp, Color.Gray, shape = CircleShape)
                                .onFocusEvent { hasFocus = it.hasFocus },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (hasFocus) {
                                Icon(
                                    imageVector = Icons.Default.Search, contentDescription = null,
                                    modifier = Modifier
                                        .size(36.dp)
                                        .padding(top = 8.dp, bottom = 8.dp, start = 15.dp)
                                )
                            }
                            BasicTextField(
                                value = searchValue,
                                onValueChange = { searchValue = it },
                                singleLine = true,
                                maxLines = 1,
                                textStyle = TextStyle(
                                    color = Color.Black,
                                    fontSize = 14.sp
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 15.dp),
                                decorationBox = { innerTextField ->
                                    if (searchValue.isEmpty()) {
                                        Text(
                                            text = stringResource(Res.string.str_search),
                                            maxLines = 1,
                                            style = TextStyle(color = Color.Gray, fontSize = 14.sp)
                                        )
                                    }
                                    innerTextField()
                                }
                            )

                            Button(
                                shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 50.dp,
                                    topEnd = 50.dp
                                ),
                                contentPadding = PaddingValues(0.dp),
                                elevation = ButtonDefaults.elevation(
                                    defaultElevation = 0.dp,
                                    pressedElevation = 0.dp,
                                    disabledElevation = 0.dp,
                                    hoveredElevation = 0.dp,
                                    focusedElevation = 0.dp,
                                ),
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.LightGray.copy(alpha = 0.3f)
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "Mic Icon",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .padding(8.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_voice),
                                contentDescription = "Mic Icon",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(22.dp)
                            )
                        }
                    }
                }

            }
        },
        actions = {
            Box {

                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = {},
                    modifier = Modifier.align(Alignment.CenterEnd).width(300.dp)
                        .padding(end = 20.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.icon_profile_photo),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp).clip(CircleShape).clickable {
                            isExpanded = true
                        }.align(Alignment.CenterEnd),
                        contentScale = ContentScale.Crop
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        UserCard()
                        Divider(paddingValues = PaddingValues(horizontal = 0.dp, vertical = 10.dp))
                        userMenus.fastForEachIndexed { index, item ->
                            DropdownMenuItem(
                                onClick = {}
                            ) {
                                MenuItem(
                                    label = stringResource(item.second),
                                    icon = item.first,
                                    tail = item.third
                                )
                            }
                            if (index in listOf(2, 4, 10, 11)) {
                                Divider(paddingValues = PaddingValues(horizontal = 0.dp, vertical = 10.dp))
                            }
                        }
                    }

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd)
                        .padding(end = 65.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if (!showSearchField) {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_voice),
                                contentDescription = "Mic Icon",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(22.dp)
                            )
                        }
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_upload_video), contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
                    }
                }
            }

        }
    )
}

@Composable
private fun UserCard() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.icon_profile_photo),
            contentDescription = null,
            modifier = Modifier.size(46.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column {
            Text(
                text = stringResource(Res.string.str_username), style =
                    MaterialTheme.typography.subtitle1, color = Color.Black,
                maxLines = 1
            )
            Text(
                text = stringResource(Res.string.str_email), style =
                    MaterialTheme.typography.subtitle1, color = Color.Black,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(Res.string.str_visit_your_channel), style =
                    MaterialTheme.typography.subtitle2, color = Color.Blue,
                maxLines = 1,
                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
            )

        }
    }
}