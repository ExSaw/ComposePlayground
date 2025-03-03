package com.exsaw.composeplayground.basic_layout.advanced_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

// children pass Constraints to one another to form result size
// limits
private val boundedConstraints = Constraints(
    minWidth = 50,
    maxWidth = 150,
    minHeight = 70,
    maxHeight = 200
)
// no limits
private val unboundedConstraints = Constraints()
private val exactConstraints = Constraints(
    minWidth = 50,
    maxWidth = 100,
    minHeight = 50,
    maxHeight = 100
)
private val combinedConstraints = Constraints(
    minWidth = 50,
    maxWidth = 50,
    minHeight = 50, // px
    maxHeight = Constraints.Infinity
)

/**
 * everything is a layout
 * * phase 1 (what) - composition (what needs to be drawn)
 * * phase 2 (where) - layout (measuring the children, positioning)
 * 1) step - measure children
 * 2) step - layout measures itself
 * 3) step - positioning children
 * * phase 3 (how) - drawing (how to draw) (render)
 */
@Composable
fun MeasurementsDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .background(Color.Red)
            .padding(16.dp) // children (affects output size)
    ) {
        Text( // children
            text = "This is a text",
            modifier = Modifier
                .background(Color.Green),
        )
        Text( // children
            text = "This is another text",
            modifier = Modifier
                .background(Color.Yellow),
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = false,
    apiLevel = 33
)
@Composable
private fun MeasurementsDemoPreview() {
    MeasurementsDemo()
}