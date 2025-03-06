package com.exsaw.composeplayground.features.modifiers_playground

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.node.invalidateMeasurement
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme

fun Modifier.improvedNegativePadding(
    horizontal: Dp
): Modifier {
    return this.then(NegativePaddingElement(horizontal))
}

data class NegativePaddingElement(
    private val horizontal: Dp
): ModifierNodeElement<NegativePaddingNode>() {

    override fun create(): NegativePaddingNode {
        return NegativePaddingNode(horizontal)
    }

    override fun update(node: NegativePaddingNode) {
        node.horizontal = horizontal
    }

}

// SKIPPABLE !!!
class NegativePaddingNode(
    var horizontal: Dp
) : LayoutModifierNode,
    Modifier.Node(),
    CompositionLocalConsumerModifierNode {

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val density = currentValueOf(LocalDensity)
        val px = with(density) {
            horizontal.roundToPx()
        }
        val placeable = measurable.measure(
            constraints = constraints.copy(
                minWidth = constraints.minWidth + px * 2,
                maxWidth = constraints.maxWidth + px * 2
            )
        )
        return layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
}

// NON Skippable !
@Composable
private fun Modifier.negativePadding(
    horizontal: Dp
): Modifier {
    val density = LocalDensity.current
    val px = with(density) {
        horizontal.roundToPx()
    }
    // intercept
    return layout { measurable, constraints ->
        val placeable = measurable.measure(
            constraints = constraints.copy(
                minWidth = constraints.minWidth + px * 2,
                maxWidth = constraints.maxWidth + px * 2
            )
        )
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
}

@Composable
fun ComplexCustomModifierDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = "This is item 1",
            modifier = Modifier
                .clickable { }
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .improvedNegativePadding(16.dp)
        )
        Text(
            text = "This is another item",
            modifier = Modifier
                .clickable { }
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .improvedNegativePadding(16.dp)
        )
        Text(
            text = "And another item",
            modifier = Modifier
                .clickable { }
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .improvedNegativePadding(16.dp)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Button(
                onClick = {}
            ) {
                Text("Button 1")
            }
            Button(
                onClick = {}
            ) {
                Text("Button 2")
            }
            Button(
                onClick = {}
            ) {
                Text("Button 3")
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = false,
    apiLevel = 33
)
@Composable
private fun ComplexCustomModifierDemoPreview() {
    ComposePlaygroundTheme {
        ComplexCustomModifierDemo()
    }
}