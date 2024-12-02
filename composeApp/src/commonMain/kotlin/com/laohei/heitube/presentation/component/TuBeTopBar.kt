package com.laohei.heitube.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import heitube.composeapp.generated.resources.*
import heitube.composeapp.generated.resources.Res
import heitube.composeapp.generated.resources.icon_profile_photo
import heitube.composeapp.generated.resources.icon_upload_video
import heitube.composeapp.generated.resources.icon_youtube
import org.jetbrains.compose.resources.painterResource

sealed class TuBeTopBarAction {
    data object ExpandAction : TuBeTopBarAction()
}

@Composable
fun TuBeTopBar(
    showSearchField: Boolean,
    onClick: (action: TuBeTopBarAction) -> Unit
) {
    var searchValue by remember { mutableStateOf("") }
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
                                .border(1.dp, Color.Gray, shape = CircleShape),// 添加圆角边框,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
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
                                    .padding(horizontal = 15.dp), // 控制输入框内边距
                                decorationBox = { innerTextField ->
                                    if (searchValue.isEmpty()) {
                                        Text(
                                            text = "Search",
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight().padding(end = 20.dp),
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
                Card(
                    shape = CircleShape
                ) {
                    Image(
                        painter = painterResource(Res.drawable.icon_profile_photo),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    )
}