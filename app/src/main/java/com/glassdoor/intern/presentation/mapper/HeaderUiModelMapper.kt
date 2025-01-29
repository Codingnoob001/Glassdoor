/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.presentation.mapper

import com.glassdoor.intern.domain.model.HeaderInfo
import com.glassdoor.intern.domain.model.ItemInfo
import com.glassdoor.intern.presentation.model.HeaderUiModel
import com.glassdoor.intern.presentation.model.ItemUiModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class HeaderUiModelMapper @Inject constructor() {

    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")

    fun toUiModel(headerInfo: HeaderInfo): HeaderUiModel = with(headerInfo) {
        HeaderUiModel(
            title = title,
            subtitle = subtitle,
            lastUpdated = lastUpdated?.let {
                Instant.ofEpochSecond(it)
                    .atZone(ZoneId.systemDefault())
                    .format(dateFormatter)
            },
            items = items.map { it.toUiModel() }
        )
    }
    private fun ItemInfo.toUiModel(): ItemUiModel = ItemUiModel(
        title = title,
        description = description,
        imageUrl = imageUrl,
        timestamp = timestampInSeconds.toString()
    )
}
