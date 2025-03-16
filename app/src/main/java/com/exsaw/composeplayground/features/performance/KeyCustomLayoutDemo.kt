package com.exsaw.composeplayground.features.performance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import com.exsaw.composeplayground.ui.theme.Typography
import org.koin.compose.KoinApplication
import kotlin.time.Duration.Companion.seconds

private data class Section(
    val id: Int,
    val header: String,
    val description: String,
)

@Composable
fun KeyCustomLayoutDemo(modifier: Modifier = Modifier) {
    val sections = remember {
        mutableStateOf(
            (0..2).map { index ->
                Section(
                    id = index,
                    header = "Section $index header",
                    description = "Section $index description",
                )
            }
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        for (section in sections.value) {
            key(section.id) { // VERY IMPORTANT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                SectionUI(
                    sectionData = section,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
        Button(
            onClick = onDebouncedClick(
                debounceTime = 1.seconds
            ) {  sections.value = sections.value.shuffled() }
        ) {
            Text("Shuffle")
        }
    }
}

@Composable
private fun SectionUI(
    sectionData: Section,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = sectionData.header,
            style = Typography.bodyLarge,
        )
        Text(
            text = sectionData.description,
            style = Typography.labelSmall,
        )
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
private fun KeyCustomLayoutDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            KeyCustomLayoutDemo(
                modifier = Modifier
                    .safeDrawingPadding()
            )
        }
    }
}