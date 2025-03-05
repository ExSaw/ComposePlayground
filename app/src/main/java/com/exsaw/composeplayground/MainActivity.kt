package com.exsaw.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.exsaw.composeplayground.features.advanced_layout.LazyLayout2dMapDemo
import com.exsaw.composeplayground.features.advanced_layout.defaultListOfLazyLayout2dMapItems

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

//            MainPageV2Screen()

//            Scaffold(
//                bottomBar = {
//                    CustomBottomNav()
//                }
//            ){}

//            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                SpacingModifierDemo(
//                    modifier = Modifier
//                        .consumeWindowInsets(innerPadding)
//                )
//            }

//            SpacingModifierDemo(
//                modifier = Modifier
//                    .statusBarsPadding()
//                    .navigationBarsPadding()
//            )

//            Scaffold(
//                Modifier
//                    .fillMaxSize(),
//                contentWindowInsets = WindowInsets.navigationBars,
//            ) { padding ->
//                FocusManagementDemo(
//                    modifier = Modifier
//                        .padding(padding)
//                )
//            }

//            Scaffold(
//                Modifier
//                    .fillMaxSize(),
//            ) { padding ->
//                LazyScrollingDemo(
//                    modifier = Modifier
//                        .padding(padding)
//                )
//            }

            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
            ) { padding ->
                val lazyLayout2dMapItems = remember {
                    defaultListOfLazyLayout2dMapItems
                }
                val lazyLayout2dMapOffset = remember {
                    mutableStateOf(IntOffset.Zero)
                }
                LazyLayout2dMapDemo(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    items = lazyLayout2dMapItems,
                    mapOffset = lazyLayout2dMapOffset.value,
                    onDrag = { offset ->
                        lazyLayout2dMapOffset.value += offset
                    }
                )
            }
        }
    }
}