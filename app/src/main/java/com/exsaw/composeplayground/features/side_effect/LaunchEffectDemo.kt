package com.exsaw.composeplayground.features.side_effect

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication


@Composable
fun LaunchEffectDemo(modifier: Modifier = Modifier) {
    val counter = remember {
        mutableIntStateOf(1)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->

    }

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.RECORD_AUDIO)
    }

    // variant 2
    // LaunchedEffect varargs ! every relaunch coroutine
    LaunchedEffect(counter.intValue) {
        if (counter.intValue % 2 == 0) {
            snackbarHostState.showSnackbar(
                "The count is even!"
            )
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        Button(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .wrapContentSize(),
            onClick = onDebouncedClick(scope) {
                counter.intValue++
// variant 1
//                if(counter.intValue % 2 == 0) {
//                    scope.launch {
//                        snackbarHostState.showSnackbar(
//                            "The number is even!"
//                        )
//                    }
//                }

            }
        ) {
            Text(
                text = "Counter=${counter.value}"
            )
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
private fun LaunchEffectDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            LaunchEffectDemo()
        }
    }
}