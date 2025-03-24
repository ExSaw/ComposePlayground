package com.exsaw.composeplayground.features.navigation_multibackstack

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.features.android_internals.service.di.musicServiceModule
import com.exsaw.composeplayground.features.bottom_nav.CustomBottomNav
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

/**
 * click item -> index to nav -> routing -> observe route -> change item
 */
@Composable
fun MultiStackExampleScreenDemo(modifier: Modifier = Modifier) {

    val rootNavController = rememberNavController()
    val navBackStackEntryFlow = rootNavController.currentBackStackEntryFlow

    val bottomNavItemsHandler: BottomNavItemsHandler = koinInject()

    Scaffold(
        bottomBar = {
            CustomBottomNav(
                itemsState = bottomNavItemsHandler.bottomNavItemsState
                    .collectAsStateWithLifecycle(),
                onClickItem = { clickedItem ->
                    bottomNavItemsHandler.onClickItem(clickedItem)
                    val route = when (clickedItem.index) {
                        0 -> "tourSearch"
                        1 -> "favoritesSearch"
                        2 -> "myTours"
                        else -> "tourSearch"
                    }
                    rootNavController.navigate(route) {
                        popUpTo(rootNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true // don't open same screen again
                        restoreState = true
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize(),

        ) { innerPadding ->
        NavHost(
            navController = rootNavController,
            startDestination = "tourSearch",
            enterTransition = { scaleIn() },
            exitTransition = { scaleOut() },
            popEnterTransition = { scaleIn() },
            popExitTransition = { scaleOut() },
        ) {
            composable(
                route = "tourSearch",
            ) {
                TourSearchNavHost()
            }
            composable(
                route = "favoritesSearch"
            ) {
                FavoritesNavHost()
            }
            composable(
                route = "myTours"
            ) {
                MyToursNavHost()
            }
        }
    }
}

@Composable
fun TourSearchNavHost() {
    val tourSearchNavController = rememberNavController()
    NavHost(
        navController = tourSearchNavController,
        startDestination = "tourSearch1",
    ) {
        for (i in 1..10) {
            composable("tourSearch$i") {
                TestScreenLayout(
                    color = { Colors.Pale.Yellow },
                    textToShow = { "Tour Search $i" },
                    onNextClick = onDebouncedClick {
                        if (i < 10) {
                            tourSearchNavController.navigate("tourSearch${i + 1}")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FavoritesNavHost() {
    val favoritesNavController = rememberNavController()
    NavHost(
        navController = favoritesNavController,
        startDestination = "favorites1",
    ) {
        for (i in 1..10) {
            composable("favorites$i") {
                TestScreenLayout(
                    color = { Colors.Pale.Green },
                    textToShow = { "Favorites $i" },
                    onNextClick = onDebouncedClick {
                        if (i < 10) {
                            favoritesNavController.navigate("favorites${i + 1}")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MyToursNavHost() {
    val myTourNavController = rememberNavController()
    NavHost(
        navController = myTourNavController,
        startDestination = "myTours1",
    ) {
        for (i in 1..10) {
            composable("myTours$i") {
                TestScreenLayout(
                    color = { Colors.Pale.Blue },
                    textToShow = { "My Tours $i" },
                    onNextClick = onDebouncedClick {
                        if (i < 10) {
                            myTourNavController.navigate("myTours${i + 1}")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TestScreenLayout(
    color: () -> Color,
    textToShow: () -> String,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = color()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = textToShow())
        Spacer(Modifier.padding(16.dp))
        Button(
            onClick = onNextClick
        ) {
            Text("Next Screen")
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait",
    fontScale = 1.0f,
)
@Composable
private fun MultiStackExampleScreenDemoPreview() {
    val context = LocalContext.current
    KoinApplication(
        application = {
            androidContext(context)
            modules(mainModule, musicServiceModule)
        }
    ) {
        ComposePlaygroundTheme {
            MultiStackExampleScreenDemo()
        }
    }
}