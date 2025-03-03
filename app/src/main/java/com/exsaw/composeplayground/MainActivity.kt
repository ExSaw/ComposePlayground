package com.exsaw.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.basic_layout.bottom_nav.BottomNavDemo1
import com.exsaw.composeplayground.basic_layout.bottom_nav.CustomBottomNav
import com.exsaw.composeplayground.basic_layout.modifiers_playground.MainPageTabsDemo
import com.exsaw.composeplayground.basic_layout.modifiers_playground.MainPageTabsDemoPreview
import com.exsaw.composeplayground.basic_layout.modifiers_playground.ModifierOrderDemo
import com.exsaw.composeplayground.basic_layout.modifiers_playground.SpacingModifierDemo
import com.exsaw.composeplayground.basic_layout.state_managment.screen.NumberGuessScreen
import com.exsaw.composeplayground.basic_layout.state_managment.screen.NumberGuessScreenRoot

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
            Scaffold(
                bottomBar = {
                    CustomBottomNav()
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0B8AFF)),
                ) {
                    MainPageTabsDemo(
                        modifier = Modifier
                            .padding(
                                top = 240.dp
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(Color.White)
                    )
                }
            }
        }
    }
}