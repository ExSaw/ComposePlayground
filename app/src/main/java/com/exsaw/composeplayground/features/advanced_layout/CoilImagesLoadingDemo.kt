package com.exsaw.composeplayground.features.advanced_layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.Image
import coil3.compose.AsyncImage
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.features.side_effect.DerivedStateDemo
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication
import kotlin.random.Random

@Composable
fun CoilImagesLoadingDemo(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(25){
            AsyncImage(
                model = "https://picsum.photos/id/${Random.nextInt(0, 1000)}/200", // will be id
                contentDescription = null,
                modifier = Modifier
                    .fillParentMaxWidth(),
                contentScale = ContentScale.Crop,
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
private fun CoilImagesLoadingDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            CoilImagesLoadingDemo()
        }
    }
}