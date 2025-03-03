package com.exsaw.composeplayground.basic_layout.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun FocusManagementDemo(modifier: Modifier = Modifier) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val refocusAction = {
        focusManager.clearFocus()
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier
            .safeDrawingPadding() // compress UI when keyboard
            // .imePadding() // compress UI when keyboard
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextField(
            value = "",
            onValueChange = {

            },
            modifier = Modifier
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, // or search
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        val isColumnFocused = remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 5.dp,
                    color = if (isColumnFocused.value) Color.Red else Color.Gray,
                )
                .padding(16.dp)
                .onFocusChanged { focusState: FocusState ->
                    isColumnFocused.value = focusState.hasFocus // any child has focus
                }
                .focusGroup(), // important moment !
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextField(
                value = "",
                onValueChange = {

                },
                modifier = Modifier,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, // or search
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            TextField(
                value = "",
                onValueChange = {

                },
                modifier = Modifier,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, // or search
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
        }
        TextField(
            value = "",
            onValueChange = {

            },
            modifier = Modifier,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, // or search
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        TextField(
            value = "",
            onValueChange = {

            },
            modifier = Modifier,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, // or search
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                }
            )
        )
        Button(
            onClick = onDebouncedClick(rememberCoroutineScope()) {
                refocusAction()
            }
        ) {
            Text(
                text = "Start filling out form"
            )
        }
        Button(
            onClick = onDebouncedClick(rememberCoroutineScope()) {
                focusManager.clearFocus()
            }
        ) {
            Text(
                text = "Clear Focus"
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = true,
    apiLevel = 33
)
@Composable
fun FocusManagementDemoPreview(modifier: Modifier = Modifier) {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            FocusManagementDemo()
        }
    }
}