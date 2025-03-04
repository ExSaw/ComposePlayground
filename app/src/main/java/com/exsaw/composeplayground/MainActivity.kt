package com.exsaw.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.exsaw.composeplayground.screen.MainPageV2Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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

            MainPageV2Screen()
        }
    }
}