package com.exsaw.composeplayground.tool

/**
 * * Created by Alexander Chudov (RickRip)
 * * usatu.robotics@gmail.com
 * * https://github.com/ExSaw
 */

import android.view.View
import androidx.compose.foundation.CombinedClickableNode
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.node.DelegatingNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.exsaw.composeplayground.core.IDispatchersProvider
import com.exsaw.composeplayground.di.CoreQualifiers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


/**
 * provides DebouncedAction for OnClick() parameter
 */
fun onDebouncedClick(
    coroutineScope: CoroutineScope,
    debounceTime: Duration? = null,
    isVibrateOnBlockedState: Boolean = true,
    action: (() -> Unit)? = null,
): () -> Unit = {
    action?.let {
        ClickDebouncer.performActionCompose(
            coroutineScope = coroutineScope,
            debounceTime = debounceTime,
            isVibrateOnBlockedState = isVibrateOnBlockedState,
            action = action
        )
    }
}

@Composable
fun Modifier.debouncedClickable(
    isEnabled: Boolean = true,
    debounceTime: Duration? = null,
    isVibrateOnBlockedState: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    role: Role? = null,
    actionOnLongClick: (() -> Unit)? = null,
    action: (() -> Unit)? = null,
): Modifier {
    val coroutineScope = rememberCoroutineScope()
    return customClickableWithIndicationIfNeeded(
        interactionSource = interactionSource,
        indication = indication
    ) { interSource, indicationNodeFactory ->
        DebouncedClickableElement(
            isEnabled = isEnabled,
            coroutineScope = coroutineScope,
            debounceTime = debounceTime,
            isVibrateOnBlockedState = isVibrateOnBlockedState,
            role = role,
            action = action,
            actionOnLongClick = actionOnLongClick,
            interactionSource = interSource,
            indication = indicationNodeFactory,
        )
    }
}

private data class DebouncedClickableElement(
    val isEnabled: Boolean,
    val coroutineScope: CoroutineScope,
    val debounceTime: Duration?,
    val isVibrateOnBlockedState: Boolean,
    val role: Role?,
    val action: (() -> Unit)?,
    val actionOnLongClick: (() -> Unit)? = null,
    val interactionSource: MutableInteractionSource?,
    val indication: IndicationNodeFactory?,
) : ModifierNodeElement<DebouncedClickableNode>() {
    override fun create(): DebouncedClickableNode {
        return DebouncedClickableNode(
            nodeCoroutineScope = coroutineScope,
            isEnabled = isEnabled,
            debounceTime = debounceTime,
            isVibrateOnBlockedState = isVibrateOnBlockedState,
            role = role,
            action = action,
            actionOnLongClick = actionOnLongClick,
            interactionSource = interactionSource,
            indication = indication,
        )
    }

    override fun update(node: DebouncedClickableNode) {
        node.update(
            isEnabled = isEnabled,
            nodeCoroutineScope = coroutineScope,
            debounceTime = debounceTime,
            isVibrateOnBlockedState = isVibrateOnBlockedState,
            action = action,
            actionOnLongClick = actionOnLongClick,
            interactionSource = interactionSource,
            indication = indication
        )
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "debouncedClickable"
        properties["enabled"] = isEnabled && (action != null || actionOnLongClick != null)
        properties["debounceTime"] = debounceTime
        properties["isActionSet"] = action != null
        properties["isLongActionSet"] = actionOnLongClick != null
        properties["isVibrateOnBlockedState"] = isVibrateOnBlockedState
        properties["role"] = role
        properties["interactionSource"] = interactionSource
        properties["indicationNodeFactory"] = indication
    }
}

private class DebouncedClickableNode(
    nodeCoroutineScope: CoroutineScope,
    val isEnabled: Boolean,
    val debounceTime: Duration?,
    val isVibrateOnBlockedState: Boolean,
    val role: Role?,
    val action: (() -> Unit)?,
    val actionOnLongClick: (() -> Unit)? = null,
    val interactionSource: MutableInteractionSource?,
    val indication: IndicationNodeFactory?,
) : DelegatingNode() {
    @OptIn(ExperimentalFoundationApi::class)
    private val clickableNode = delegate(
        CombinedClickableNode(
            enabled = isEnabled(
                isEnabled = isEnabled,
                action = action,
                actionOnLongClick = actionOnLongClick
            ),
            onClick = onDebouncedClick(
                coroutineScope = nodeCoroutineScope,
                debounceTime = debounceTime,
                isVibrateOnBlockedState = isVibrateOnBlockedState,
                action = action
            ),
            onLongClick = onDebouncedClick(
                coroutineScope = nodeCoroutineScope,
                debounceTime = debounceTime,
                isVibrateOnBlockedState = isVibrateOnBlockedState,
                action = actionOnLongClick
            ),
            onDoubleClick = null,
            interactionSource = interactionSource,
            indicationNodeFactory = indication,
            onClickLabel = null,
            onLongClickLabel = null,
            role = role
        )
    )

    @OptIn(ExperimentalFoundationApi::class)
    fun update(
        isEnabled: Boolean,
        nodeCoroutineScope: CoroutineScope,
        debounceTime: Duration?,
        isVibrateOnBlockedState: Boolean,
        action: (() -> Unit)?,
        actionOnLongClick: (() -> Unit)? = null,
        interactionSource: MutableInteractionSource?,
        indication: IndicationNodeFactory?,
    ) {
        clickableNode.update(
            enabled = isEnabled(
                isEnabled = isEnabled,
                action = action,
                actionOnLongClick = actionOnLongClick
            ),
            onClick = onDebouncedClick(
                coroutineScope = nodeCoroutineScope,
                debounceTime = debounceTime,
                isVibrateOnBlockedState = isVibrateOnBlockedState,
                action = action
            ),
            onLongClick = onDebouncedClick(
                coroutineScope = nodeCoroutineScope,
                debounceTime = debounceTime,
                isVibrateOnBlockedState = isVibrateOnBlockedState,
                action = actionOnLongClick
            ),
            onDoubleClick = null,
            interactionSource = interactionSource,
            indicationNodeFactory = indication,
            onClickLabel = null,
            onLongClickLabel = null,
            role = null
        )
    }

    private fun isEnabled(
        isEnabled: Boolean,
        action: (() -> Unit)?,
        actionOnLongClick: (() -> Unit)?
    ): Boolean = isEnabled && (action != null || actionOnLongClick != null)
}

@Deprecated(
    message = "Unstable variation for test purposes",
    replaceWith = ReplaceWith("Modifier.debouncedClickable()"),
    level = DeprecationLevel.WARNING
)
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.debouncedClickableUnstable(
    debounceTime: Duration? = null,
    isVibrateOnBlockedState: Boolean = true,
    actionOnLongClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    action: (() -> Unit)? = null,
): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()
    val debouncedActionOnClick = onDebouncedClick(
        coroutineScope = coroutineScope,
        debounceTime = debounceTime,
        isVibrateOnBlockedState = isVibrateOnBlockedState,
        action = action
    )
    val debouncedActionOnLongClick = onDebouncedClick(
        coroutineScope = coroutineScope,
        debounceTime = debounceTime,
        isVibrateOnBlockedState = isVibrateOnBlockedState,
        action = actionOnLongClick
    )
    Modifier.combinedClickable(
        enabled = action != null || actionOnLongClick != null,
        onClick = { debouncedActionOnClick.invoke() },
        onLongClick = { debouncedActionOnLongClick.invoke() },
        indication = indication,
        interactionSource = interactionSource,
    )
}

fun View.setDebouncedClickListener(
    debounceTime: Duration? = null,
    isVibrateOnBlockedState: Boolean = true,
    actionOnLongClick: ((View) -> Unit)? = null,
    action: ((View) -> Unit)?
) {
    isClickable = if (action != null || actionOnLongClick != null) {
        if (action != null) setOnClickListener(
            getDebouncedClickListener(
                view = this,
                debounceTime = debounceTime,
                isVibrateOnBlockedState = isVibrateOnBlockedState
            ) { action(this) }
        )
        if (actionOnLongClick != null) setOnLongClickListener(
            getDebouncedLongClickListener(
                view = this,
                debounceTime = debounceTime
            ) { actionOnLongClick(this) }
        )
        else setOnLongClickListener { true }
        true
    } else {
        setOnClickListener(null)
        false
    }
}

private fun getDebouncedClickListener(
    view: View,
    debounceTime: Duration? = null,
    isVibrateOnBlockedState: Boolean,
    action: (() -> Unit)
): View.OnClickListener {
    return View.OnClickListener {
        ClickDebouncer.performAction(
            view = view,
            debounceTime = debounceTime,
            isVibrateOnBlockedState = isVibrateOnBlockedState,
            action = action
        )
    }
}

private fun getDebouncedLongClickListener(
    view: View,
    debounceTime: Duration?,
    action: (() -> Unit)
): View.OnLongClickListener {
    return View.OnLongClickListener {
        ClickDebouncer.performAction(
            view = view,
            debounceTime = debounceTime,
            isVibrateOnBlockedState = false,
            action = action
        )
        true
    }
}

/**
 * Observable of current blocked state
 */
fun getBlockedStateFlow(): StateFlow<Boolean> = ClickDebouncer.isBlockedState

/**
 * Force block debouncer for [debounceTime] period
 */
fun emitClickToDebouncer(debounceTime: Duration? = null) {
    ClickDebouncer.blockInput(true, debounceTime)
}

/**
 * Global Click Debouncer for handling click blocking
 */
private object ClickDebouncer : KoinComponent {

    private val DEFAULT_CLICK_DEBOUNCE_TIME = 100.milliseconds
    private val dispatchers: IDispatchersProvider by inject()
    private val appScope: CoroutineScope by inject(CoreQualifiers.APP_SCOPE.qualifier)
    private val vibrator: Vibrator by inject()

    private var clickJob: Job? = null

    private val _isBlockedState = MutableStateFlow(ClickDebouncerStateData())
    @OptIn(ExperimentalCoroutinesApi::class)
    val isBlockedState = _isBlockedState.mapLatest { it.isBlocked }
        .stateIn(
            scope = appScope.plus(dispatchers.default),
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )

    init {
        appScope.launch(dispatchers.default) {
            _isBlockedState
                .filter { it.isBlocked }
                .collectLatest {
                    delay(it.debounceTime)
                    _isBlockedState.update {
                        it.copy(
                            isBlocked = false,
                            debounceTime = DEFAULT_CLICK_DEBOUNCE_TIME
                        )
                    }
                }
        }
    }

    fun performAction(
        view: View,
        debounceTime: Duration?,
        isVibrateOnBlockedState: Boolean,
        action: (() -> Unit)
    ) {
        when {
            (!isBlockedState.value && (clickJob?.isCompleted == true || clickJob == null)) -> {
                blockInput(true, debounceTime)
                clickJob = view.findViewTreeLifecycleOwner()
                    ?.lifecycleScope
                    ?.launch {
                        action.invoke()
                    }
            }

            isVibrateOnBlockedState -> {
                vibrator.vibrate(
                    duration = 50L,
                    pattern = 0,
                    isCutItself = true
                )
            }
        }
    }

    fun performActionCompose(
        coroutineScope: CoroutineScope,
        debounceTime: Duration?,
        isVibrateOnBlockedState: Boolean,
        action: (() -> Unit)
    ) {
        when {
            (!isBlockedState.value && (clickJob?.isCompleted == true || clickJob == null)) -> {
                blockInput(true, debounceTime)
                clickJob = coroutineScope.launch { action.invoke() }
            }

            isVibrateOnBlockedState -> {
                vibrator.vibrate(
                    duration = 50L,
                    pattern = 0,
                    isCutItself = true
                )
            }
        }
    }

    fun blockInput(
        isBlock: Boolean,
        debounceTime: Duration?
    ) {
        _isBlockedState.update { it.copy(
            isBlocked = isBlock,
            debounceTime = debounceTime ?: DEFAULT_CLICK_DEBOUNCE_TIME
        ) }
    }

    private data class ClickDebouncerStateData(
        val isBlocked: Boolean = false,
        val debounceTime: Duration = DEFAULT_CLICK_DEBOUNCE_TIME
    )
}

/**
 * Utility Modifier factory that handles edge cases for [interactionSource], and [indication].
 * [createClickable] is the lambda that creates the actual clickable element, which will be chained
 * with [Modifier.indication] if needed.
 */
private inline fun Modifier.customClickableWithIndicationIfNeeded(
    interactionSource: MutableInteractionSource?,
    indication: Indication?,
    crossinline createClickable: (MutableInteractionSource?, IndicationNodeFactory?) -> Modifier
): Modifier {
    return this then when {
        // Fast path - indication is managed internally
        indication is IndicationNodeFactory -> createClickable(interactionSource, indication)
        // Fast path - no need for indication
        indication == null -> createClickable(interactionSource, null)
        // Non-null Indication (not IndicationNodeFactory) with a non-null InteractionSource
        interactionSource != null -> Modifier
            .indication(interactionSource, indication)
            .then(createClickable(interactionSource, null))
        // Non-null Indication (not IndicationNodeFactory) with a null InteractionSource, so we need
        // to use composed to create an InteractionSource that can be shared. This should be a rare
        // code path and can only be hit from new callers.
        else -> Modifier.composed {
            val newInteractionSource = remember { MutableInteractionSource() }
            Modifier
                .indication(newInteractionSource, indication)
                .then(createClickable(newInteractionSource, null))
        }
    }
}