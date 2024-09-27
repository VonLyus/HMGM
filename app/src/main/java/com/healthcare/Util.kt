package com.healthcare

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

object Util {
    fun Modifier.horizontalMirror(): Modifier = layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(placeable.width, 0)
        }
    }
}