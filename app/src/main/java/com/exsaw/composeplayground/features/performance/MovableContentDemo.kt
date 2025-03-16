package com.exsaw.composeplayground.features.performance

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.exsaw.composeplayground.R
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication
import kotlin.time.Duration.Companion.seconds

private data class ProfileData(
    val userName: String = "John Doe",
    @DrawableRes val picture: Int = R.drawable.android,
)

@Composable
fun MovableContentDemo(modifier: Modifier = Modifier) {
    val isCondensedView = remember {
        mutableStateOf(true)
    }
    val profileData = remember {
        mutableStateOf(ProfileData())
    }
    val movableProfileImage = remember {
        movableContentWithReceiverOf<Modifier>  {
            ProfileImage(
                pictureResId = profileData.value.picture,
                modifier = this
            ) // doesn't change
        }
    }
    val movableUserName = remember {
        movableContentOf {
            Text(profileData.value.userName) // doesn't change
        }
    }
    Column(
        modifier = modifier
    ) {
        if(isCondensedView.value) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                movableProfileImage(Modifier.size(128.dp))
                Spacer(modifier = Modifier.width(16.dp))
                movableUserName()
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                movableProfileImage(Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                movableUserName()
            }
        }
        Button(
            onClick = onDebouncedClick(1.seconds) {
                isCondensedView.value = !isCondensedView.value
            }
        ) {
            Text("Switch row/column")
        }
    }
}

@Composable
private fun ProfileImage(
    @DrawableRes pictureResId: Int,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = pictureResId,
        contentDescription = "profile pic",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape),
    )
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
private fun MovableContentDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            MovableContentDemo(
                modifier = Modifier
                    .safeDrawingPadding()
            )
        }
    }
}