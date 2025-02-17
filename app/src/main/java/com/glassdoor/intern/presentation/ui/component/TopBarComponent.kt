/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.presentation.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.glassdoor.intern.R
import com.glassdoor.intern.presentation.theme.InternTheme
import com.glassdoor.intern.utils.previewParameterProviderOf

private val progressIndicatorStrokeWidth: Dp = 3.dp

@Composable
internal fun TopBarComponent(
    isLoading: Boolean,
    progressClickAction: () -> Unit,
    modifier: Modifier = Modifier,
) = TopAppBar(
    modifier = modifier,
    title = {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    },
    actions = {
        ProgressIndicatorComponent(
            isLoading = isLoading,
            progressClickAction = progressClickAction,
        )
    },
)

@Composable
private fun ProgressIndicatorComponent(
    isLoading: Boolean,
    progressClickAction: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
) = IconButton(
    modifier = modifier,
    enabled = !isLoading,
    onClick = progressClickAction,
) {
    Crossfade(
        modifier = Modifier.padding(InternTheme.dimensions.normal),
        targetState = isLoading,
        label = "ProgressIndicatorComponent",
    ) { isLoadingState ->
        if (isLoadingState) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = color,
                strokeWidth = progressIndicatorStrokeWidth,
            )
        } else {
            Icon(
                modifier = Modifier.fillMaxSize(),
                contentDescription = stringResource(R.string.accessibility_navigation_refresh_screen_content),
                imageVector = Icons.Default.Refresh,
                tint = color,
            )
        }
    }
}

@Preview
@Composable
private fun TopBarComponentPreview(
    @PreviewParameter(TopBarComponentPreviewParameterProvider::class) isLoading: Boolean
) = InternTheme {
    TopBarComponent(
        isLoading = isLoading,
        progressClickAction = {}
    )
}

private class TopBarComponentPreviewParameterProvider :
    PreviewParameterProvider<Boolean> by previewParameterProviderOf(
        true, false
    )
