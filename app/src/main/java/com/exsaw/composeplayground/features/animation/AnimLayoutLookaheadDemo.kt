package com.exsaw.composeplayground.features.animation

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.DeferredTargetAnimation
import androidx.compose.animation.core.ExperimentalAnimatableApi
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ApproachLayoutModifierNode
import androidx.compose.ui.layout.ApproachMeasureScope
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.Colors
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication


@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = false,
    apiLevel = 33,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait",
    fontScale = 1.0f,
)
@Composable
private fun AnimLayoutLookaheadDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            AnimLayoutLookaheadDemo()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalAnimatableApi::class)
@Composable
fun AnimLayoutLookaheadDemo(modifier: Modifier = Modifier) {
    val horizontalArrangementState = remember {
        mutableStateOf(Arrangement.Start)
    }
    val dpIncrementState = remember {
        mutableStateOf(0.dp)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // works with any layout!
        LookaheadScope {
            FlowColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                horizontalArrangement = horizontalArrangementState.value,
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp + dpIncrementState.value)
                        .animateLayoutChanges(scope = this@LookaheadScope)
                        .background(Colors.Bright.Red)
                )
                Box(
                    modifier = Modifier
                        .size(100.dp + dpIncrementState.value)
                        .animateLayoutChanges(scope = this@LookaheadScope)
                        .background(Colors.Bright.Green)
                )
                Box(
                    modifier = Modifier
                        .size(100.dp + dpIncrementState.value)
                        .animateLayoutChanges(scope = this@LookaheadScope)
                        .background(Colors.Bright.Blue)
                )
            }
        }

        // original
        AnimatedFlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = horizontalArrangementState.value
        ) {// LookaheadScope
            Box(
                modifier = Modifier
                    .size(100.dp + dpIncrementState.value)
                    .animateLayoutChanges(scope = this)
                    .background(Colors.Bright.Red)
            )
            Box(
                modifier = Modifier
                    .size(100.dp + dpIncrementState.value)
                    .animateLayoutChanges(scope = this)
                    .background(Colors.Bright.Green)
            )
            Box(
                modifier = Modifier
                    .size(100.dp + dpIncrementState.value)
                    .animateLayoutChanges(scope = this)
                    .background(Colors.Bright.Blue)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = onDebouncedClick {
                    horizontalArrangementState.value = Arrangement.Start
                }
            ) {
                Text("Start")
            }
            Button(
                onClick = onDebouncedClick {
                    horizontalArrangementState.value = Arrangement.Center
                }
            ) {
                Text("Center")
            }
            Button(
                onClick = onDebouncedClick {
                    horizontalArrangementState.value = Arrangement.End
                }
            ) {
                Text("End")
            }
            Button(
                onClick = onDebouncedClick {
                    horizontalArrangementState.value = Arrangement.SpaceBetween
                }
            ) {
                Text("SpaceBetween")
            }
            Button(
                onClick = onDebouncedClick {
                    horizontalArrangementState.value = Arrangement.SpaceAround
                }
            ) {
                Text("SpaceAround")
            }
            Button(
                onClick = onDebouncedClick {
                    horizontalArrangementState.value = Arrangement.SpaceEvenly
                }
            ) {
                Text("SpaceEvenly")
            }
            Button(
                onClick = onDebouncedClick {
                    dpIncrementState.value -= 10.dp
                }
            ) {
                Text("DP--")
            }
            Button(
                onClick = onDebouncedClick {
                    dpIncrementState.value += 10.dp
                }
            ) {
                Text("DP++")
            }

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnimatedFlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable LookaheadScope.() -> Unit,
) {
    LookaheadScope {
        FlowRow(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = horizontalArrangement
        ) {
            content()
        }
    }
}


@OptIn(ExperimentalAnimatableApi::class)
private fun Modifier.animateLayoutChanges(
    scope: LookaheadScope,
    posAnim: DeferredTargetAnimation<IntOffset, AnimationVector2D> =
        DeferredTargetAnimation(IntOffset.VectorConverter),
    sizeAnim: DeferredTargetAnimation<IntSize, AnimationVector2D> =
        DeferredTargetAnimation(IntSize.VectorConverter),
): Modifier {
    return then(
        AnimateLayoutChangesElement(
            scope = scope,
            posAnim = posAnim,
            sizeAnim = sizeAnim
        )
    )
}

@OptIn(ExperimentalAnimatableApi::class)
private data class AnimateLayoutChangesElement(
    private val scope: LookaheadScope,
    private val posAnim: DeferredTargetAnimation<IntOffset, AnimationVector2D>,
    private val sizeAnim: DeferredTargetAnimation<IntSize, AnimationVector2D>,
) : ModifierNodeElement<AnimateLayoutChangesNode>() {
    override fun create(): AnimateLayoutChangesNode {
        return AnimateLayoutChangesNode(
            scope = scope,
            posAnim = posAnim,
            sizeAnim = sizeAnim
        )
    }

    override fun update(node: AnimateLayoutChangesNode) = Unit
}

@OptIn(ExperimentalAnimatableApi::class)
private class AnimateLayoutChangesNode(
    private val scope: LookaheadScope,
    private val posAnim: DeferredTargetAnimation<IntOffset, AnimationVector2D>,
    private val sizeAnim: DeferredTargetAnimation<IntSize, AnimationVector2D>,
) : ApproachLayoutModifierNode, Modifier.Node() {

    override fun Placeable.PlacementScope.isPlacementApproachInProgress(
        lookaheadCoordinates: LayoutCoordinates
    ): Boolean {
        val targetOffset = with(scope) {
            lookaheadScopeCoordinates.localLookaheadPositionOf(
                sourceCoordinates = lookaheadCoordinates,
            )
        }
        posAnim.updateTarget(
            target = targetOffset.round(),
            coroutineScope = coroutineScope,
            animationSpec = tween(1500)
        )
        return !posAnim.isIdle // isPlacementApproachInProgress = !posAnim.isIdle
    }

    override fun isMeasurementApproachInProgress(
        lookaheadSize: IntSize
    ): Boolean {
        sizeAnim.updateTarget(
            target = lookaheadSize,
            coroutineScope = coroutineScope,
            animationSpec = tween(1500)
        )
        return !sizeAnim.isIdle // isMeasurementApproachInProgress = !sizeAnim.isIdle
    }

    override fun ApproachMeasureScope.approachMeasure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val (width, height) = sizeAnim.updateTarget(
            target = lookaheadSize,
            coroutineScope = coroutineScope,
            animationSpec = tween(1500)
        )
        val animatedConstraints = Constraints.fixed(width, height)
        val placeable = measurable.measure(animatedConstraints)

        return with(scope) {
            layout(placeable.width, placeable.height) {
                coordinates?.let {
                    val targetOffset = lookaheadScopeCoordinates.localLookaheadPositionOf(
                        sourceCoordinates = it,
                    )
                    val animatedOffset = posAnim.updateTarget(
                        target = targetOffset.round(),
                        coroutineScope = coroutineScope,
                        animationSpec = tween(1500)
                    )
                    val currentOffset = lookaheadScopeCoordinates.localPositionOf(
                        sourceCoordinates = it
                    )
                    val (x, y) = animatedOffset - currentOffset.round()
                    placeable.place(x, y)
                } ?: placeable.place(0, 0)
            }
        }
    }
}