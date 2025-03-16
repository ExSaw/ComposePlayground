package com.exsaw.composeplayground.features.composition_locals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.debouncedClickable
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.scope.rememberKoinScope

val LocalSnackBarState = staticCompositionLocalOf {
    SnackbarHostState()
}

@Composable
fun CompositionLocalDemo(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val state = LocalSnackBarState.current
    Scaffold(
        snackbarHost = {
            SnackbarHost(state)
        }
    ) { innerPadding ->
        Button(
            modifier = Modifier
                .padding(innerPadding),
            onClick =  {
                coroutineScope.launch {
                    state.showSnackbar("Snackbar")
                }
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Colors.Bright.Red
            )
        ) {

            val contentColor = LocalContentColor.current // will be RED
            val textStyle = LocalTextStyle.current
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
            )
            Text(
                text = "Hello World"
            )

            CompositionLocalProvider(
                LocalContentColor provides Colors.Bright.Green
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                )
                Text(
                    text = "Hello World"
                )
            }

        }
    }

}

@Composable
private fun CustomTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
            )
        ) {
            title()
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
private fun CompositionLocalDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            Column {
                CustomTopAppBar(
                    title = {
                        Text("CustomTopAppBar")
                    }
                )
                CompositionLocalDemo()
            }
        }
    }
}