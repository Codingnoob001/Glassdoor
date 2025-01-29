/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.presentation.ui.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.glassdoor.intern.presentation.model.HeaderUiModel
import com.glassdoor.intern.presentation.model.ItemUiModel
import com.glassdoor.intern.presentation.theme.InternTheme
import com.glassdoor.intern.utils.previewParameterProviderOf

private val headerBorderStrokeWidth: Dp = 3.dp
private val imageSize: Dp = 120.dp

@Composable
internal fun ContentComponent(
    header: HeaderUiModel,
    items: List<ItemUiModel>,
    modifier: Modifier = Modifier,
) = Column(modifier = modifier) {
    HeaderComponent(
        modifier = Modifier
            .padding(horizontal = InternTheme.dimensions.normal)
            .padding(top = InternTheme.dimensions.normal),
        header = header,
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1F),
        contentPadding = PaddingValues(InternTheme.dimensions.double),
        verticalArrangement = Arrangement.spacedBy(InternTheme.dimensions.double),
    ) {
        items(
            items = items,
            key = { it.key },
            contentType = { "ItemComponent" },
            itemContent = { item -> ItemComponent(item) },
        )
    }
}


@Composable
private fun HeaderComponent(
    header: HeaderUiModel,
    modifier: Modifier = Modifier,
) = AnimatedVisibility(
    modifier = modifier,
    enter = fadeIn(),
    exit = fadeOut(),
    label = "HeaderComponent",
    visible = !header.isEmpty,
) {
    Card(
        border = BorderStroke(
            width = headerBorderStrokeWidth,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        with(header) {
            Column(modifier = Modifier.padding(InternTheme.dimensions.normal)) {
                header.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}


@Composable
private fun ItemComponent(item: ItemUiModel) = Card {
    with(item) {
        Row(
            modifier = Modifier.padding(InternTheme.dimensions.double),
            horizontalArrangement = Arrangement.spacedBy(InternTheme.dimensions.double),
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = title,
                style = MaterialTheme.typography.titleSmall,
            )

            Text(
                text = timestamp,
                style = MaterialTheme.typography.labelSmall,
            )
        }

        Row(
            modifier = Modifier
                .padding(bottom = InternTheme.dimensions.double)
                .padding(horizontal = InternTheme.dimensions.double),
            horizontalArrangement = Arrangement.spacedBy(InternTheme.dimensions.double),
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = description,
                style = MaterialTheme.typography.bodySmall,
            )

            AsyncImage(
                modifier = Modifier
                    .align(Alignment.Top)
                    .size(imageSize)
                    .clip(CardDefaults.shape),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                error = rememberVectorPainter(Icons.Default.Warning),
                model = imageUrl,
            )
        }
    }
}

@Preview
@Composable
private fun ContentComponentPreview(
    @PreviewParameter(ContentComponentPreviewParameterProvider::class)
    headerAndItems: HeaderAndItems
) = InternTheme {
    ContentComponent(
        header = headerAndItems.first,
        items = headerAndItems.second,
    )
}


@Preview
@Composable
private fun HeaderComponentPreview(
    @PreviewParameter(HeaderComponentPreviewParameterProvider::class) header: HeaderUiModel
) = InternTheme {
    HeaderComponent(header = header)
}


@Preview
@Composable
private fun ItemComponentPreview(
    @PreviewParameter(ItemComponentPreviewParameterProvider::class) item: ItemUiModel
) = InternTheme {
    ItemComponent(item)
}

private typealias HeaderAndItems = Pair<HeaderUiModel, List<ItemUiModel>>

private class ContentComponentPreviewParameterProvider :
    PreviewParameterProvider<HeaderAndItems> by previewParameterProviderOf(
        HeaderAndItems(
            HeaderUiModel(
                title = "Header Title",
                subtitle = "Header Subtitle",
                lastUpdated = "10:00 AM",
                items = listOf(
                    ItemUiModel(
                        title = "Item Title 11",
                        description = "Item Description 1",
                        imageUrl = "https://via.placeholder.com/150",
                        timestamp = "10:00"
                    ),
                    ItemUiModel(
                        title = "Item Title 22",
                        description = "Item Description 2",
                        imageUrl = "https://via.placeholder.com/150",
                        timestamp = "11:00"
                    )
                )
            ),
            listOf(
                ItemUiModel(
                    title = "Item Title 33",
                    description = "Item Description 3",
                    imageUrl = "https://via.placeholder.com/150",
                    timestamp = "12:00"
                ),
                ItemUiModel(
                    title = "Item Title 4",
                    description = "Item Description 4",
                    imageUrl = "https://via.placeholder.com/150",
                    timestamp = "01:00"
                )
            )
        )
    )


private class HeaderComponentPreviewParameterProvider :
    PreviewParameterProvider<HeaderUiModel> by previewParameterProviderOf(
        HeaderUiModel(
            title = "Header Title",
            subtitle = "Header Subtitle",
            lastUpdated = "10:00 AM",
            items = listOf(
                ItemUiModel(
                    title = "Item Title 1",
                    description = "Item Description 1",
                    imageUrl = "https://via.placeholder.com/150",
                    timestamp = "10:00"
                ),
                ItemUiModel(
                    title = "Item Title 2",
                    description = "Item Description 2",
                    imageUrl = "https://via.placeholder.com/150",
                    timestamp = "11:00"
                )
            )
        )
    )


private class ItemComponentPreviewParameterProvider :
    PreviewParameterProvider<ItemUiModel> by previewParameterProviderOf(
        ItemUiModel(
            title = "Item Title 0",
            description = "Item Description 0",
            imageUrl = null,
            timestamp = "10:00",
        ),
    )
