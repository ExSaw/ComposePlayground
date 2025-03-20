package com.exsaw.composeplayground.features.android_internals

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.logUnlimited
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

@Composable
fun AndroidInternalsBroadcast(
    modifier: Modifier = Modifier,
) {
    val context = LocalActivity.current
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Button(
                onClick = onDebouncedClick {
                    context?.sendBroadcast(
                        Intent().apply {
                            `package` = "com.exsaw.composeplayground2"
                            action = "com.exsaw.ACTION_GREET_VIA_BROADCAST"
                            putExtra("greeting_string", "Hello From Compose Playground 1")
                        }
                    ) // explicit broadcast
                    logUnlimited("---SEND BROADCAST->AndroidInternalsBroadcast->")
                }
            ) {
                Text("Send Broadcast")
            }
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
private fun AndroidInternalsBroadcastPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            AndroidInternalsBroadcast()
        }
    }
}