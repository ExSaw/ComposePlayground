package com.exsaw.composeplayground.features.android_internals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.exsaw.composeplayground.ScreenA
import com.exsaw.composeplayground.ScreenB
import com.exsaw.composeplayground.ScreenBAndCGraph
import com.exsaw.composeplayground.ScreenC
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import com.exsaw.composeplayground.util.koinNavSharedViewModel
import com.exsaw.composeplayground.viewmodel.MainViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

// sharedViewmodel for navBackStack entry
@Composable
fun AndroidInternalsCounterRoot(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            startDestination = ScreenA,
        ) {
            composable<ScreenA> { backStackEntry ->

                val viewModel: MainViewModel = koinViewModel()

                val screenBCounter = backStackEntry.savedStateHandle.getStateFlow(
                    "screen_b_counter", 0
                ).collectAsStateWithLifecycle()

                Column {
                    AndroidInternalsCounterUi(
                        screenName = "Screen A",
                        counterState = viewModel.counterState.collectAsState(),
                        onIncrementClick = {
                            viewModel.increaseCounter()
                        },
                        modifier = Modifier
                            .weight(1f)
                    )
                    Button(
                        onClick = onDebouncedClick {
                            navController.navigate(ScreenBAndCGraph)
                        }
                    ) {
                        Text("Navigate to ScreenBAndCGraph")
                    }
                    Text("The counter on screenB was ${screenBCounter.value}")
                }
            }

            // nested nav
            navigation<ScreenBAndCGraph>(
                startDestination = ScreenB
            ) {
                composable<ScreenB> { navBackStackEntry ->
                    val sharedViewModel: MainViewModel =
                        navBackStackEntry.koinNavSharedViewModel(navController)
                    val counterState =
                        sharedViewModel.counterState.collectAsStateWithLifecycle()
                    LaunchedEffect(counterState.value) {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("screen_b_counter", counterState.value)
                    }
                    Column {
                        AndroidInternalsCounterUi(
                            screenName = "Screen B",
                            counterState = counterState,
                            onIncrementClick = {
                                sharedViewModel.increaseCounter()
                            },
                            modifier = Modifier
                                .weight(1f)
                        )
                        Button(
                            onClick = onDebouncedClick {
                                navController.navigate(ScreenC("Hello World"))
                            }
                        ) {
                            Text("Navigate to Screen C")
                        }
                    }
                }
                composable<ScreenC> { navBackStackEntry ->
                    val sharedViewModel: MainViewModel =
                        navBackStackEntry.koinNavSharedViewModel(navController)
                    AndroidInternalsCounterUi(
                        screenName = "Screen C",
                        counterState = sharedViewModel.counterState.collectAsStateWithLifecycle(),
                        onIncrementClick = {
                            sharedViewModel.increaseCounter()
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun AndroidInternalsCounterUi(
    screenName: String,
    counterState: State<Int>,
    onIncrementClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(screenName)
        Button(
            onClick = onDebouncedClick { onIncrementClick() },
        ) {
            Text(text = "Counter ${counterState.value}")
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = false,
    apiLevel = 33,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait",
    fontScale = 1.0f,
)
@Composable
private fun AndroidInternalsCounterUiPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        val counterState = remember {
            mutableIntStateOf(0)
        }
        ComposePlaygroundTheme {
            AndroidInternalsCounterUi(
                screenName = "Preview",
                counterState = counterState,
                onIncrementClick = {
                    counterState.intValue++
                },
            )
        }
    }
}