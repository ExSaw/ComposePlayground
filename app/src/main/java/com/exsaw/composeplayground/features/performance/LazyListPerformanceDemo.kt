package com.exsaw.composeplayground.features.performance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.debouncedClickable
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication

private data class MyListItem(
    val id: Int,
    val title: String,
    val description: String,
)

@Composable
fun LazyListPerformanceDemo(modifier: Modifier = Modifier) {
    var myList by remember {
        mutableStateOf(
            (0..20).map {
                MyListItem(
                    id = it,
                    title = "List item $it",
                    description = "Description $it"
                )
            }
        )
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onDebouncedClick {
                    val index = myList.maxBy { it.id }.id + 1
                    myList = listOf(
                        MyListItem(
                            id = index,
                            title = "List item $index",
                            description = "Description $index"
                        )
                    ) + myList
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(
                items = myList,
                key = { item -> item.id } // VERY IMPORTANT ! ! ! ! ! ! ! ! ! ! ! ! ! ! !
            ) { item ->
                ListItem(
                    headlineContent = {
                        Text(item.title)
                    },
                    supportingContent = {
                        Text(item.description)
                    },
                    trailingContent = {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(30.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(25.dp)
                                    .debouncedClickable {
                                        myList -= item
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    tint = Colors.Bright.Green,
                                    contentDescription = "Delete",
                                )
                            }
                            IconButton(
                                modifier = Modifier
                                    .size(25.dp),
                                onClick = onDebouncedClick {
                                    myList -= item
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    tint = Colors.Bright.Red,
                                    contentDescription = "Delete",
                                )
                            }
                        }
                    }
                )
            }
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
private fun LazyListPerformanceDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            LazyListPerformanceDemo()
        }
    }
}