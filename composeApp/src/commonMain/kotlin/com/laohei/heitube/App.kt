package com.laohei.heitube

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.laohei.heitube.core.presentation.AdaptiveWindow
import com.laohei.heitube.core.presentation.NavShape
import com.laohei.heitube.presentation.component.SideMenuList
import com.laohei.heitube.presentation.component.SideMenuRail
import com.laohei.heitube.presentation.component.TuBeTopBar
import com.laohei.heitube.presentation.component.TuBeTopBarAction
import com.laohei.heitube.presentation.home.HomePage
import com.laohei.heitube.presentation.subscription.SubscriptionPage
import com.laohei.heitube.ui.theme.FontSans
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        typography = Typography(defaultFontFamily = FontSans())
    ) {
        BoxWithConstraints {
            val adaptiveWindow = AdaptiveWindow.adaptive(maxWidth)
            var isExpend by remember { mutableStateOf(true) }
//            val lo = maxWidth
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TuBeTopBar(
                        showSearchField = !(adaptiveWindow == AdaptiveWindow.Small && maxWidth < 660.dp)
                    ) {
                        when (it) {
                            TuBeTopBarAction.ExpandAction -> {
                                if (adaptiveWindow == AdaptiveWindow.Large) {
                                    isExpend = !isExpend
                                } else {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                }
                            }
                        }
                    }
                },
                drawerShape = NavShape(260.dp, 0f),
                drawerContent = {
                    SideMenuList(
                        paddingValues = PaddingValues(vertical = 15.dp),
                        navigateToRoute = {}
                    )
                }
            ) {
//                Column {
//                    Text(text = lo.toString())
                Row(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    when (adaptiveWindow) {
                        AdaptiveWindow.Small -> {}
                        AdaptiveWindow.Middle -> {
                            SideMenuRail()
                        }

                        AdaptiveWindow.Large -> {
                            if (isExpend) {
                                SideMenuList(
                                    navigateToRoute = { screen -> navController.navigate(screen) }
                                )
                            } else {
                                SideMenuRail()
                            }
                        }
                    }

                    BoxWithConstraints {
                        val count = when (adaptiveWindow) {
                            AdaptiveWindow.Small -> if (maxWidth > 700.dp) 3 else if (maxWidth > 500.dp) 2 else 1
                            AdaptiveWindow.Middle -> if (maxWidth > 1000.dp) 4 else 3
                            AdaptiveWindow.Large -> if (maxWidth > 1270.dp || !isExpend) 5 else 4
                        }
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home
                        ) {
                            composable<Screen.Home> { HomePage(count = count) }
                            composable<Screen.Subscription> { SubscriptionPage(subscription = it.toRoute<Screen.Subscription>()) }
                        }
                    }
                }
            }
//            }
        }
    }
}