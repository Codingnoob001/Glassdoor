/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.data.mapper

import com.glassdoor.intern.data.model.HeaderInfoDto
import com.glassdoor.intern.data.model.ItemInfoDto
import com.glassdoor.intern.domain.model.HeaderInfo
import com.glassdoor.intern.domain.model.ItemInfo
import com.glassdoor.intern.domain.model.Status
import java.time.Instant
import javax.inject.Inject

internal class HeaderInfoMapper @Inject constructor() {


    fun toDomain(header: HeaderInfoDto, items: List<ItemInfoDto>): HeaderInfo = with(header) {
        HeaderInfo(
            title = title ?: "Untitled",
            subtitle = subtitle ?: "No subtitle available",
            lastUpdated = lastUpdated?.let { Instant.parse(it).epochSecond },
            status = status?.let { Status.valueOf(it.uppercase()) } ?: Status.ERROR,
            items = items.map(::toDomain)
        )
    }

    private fun toDomain(item: ItemInfoDto): ItemInfo = with(item) {
        ItemInfo(
            title = title,
            description = description,
            imageUrl = imageUrl,
            timestampInSeconds = Instant.parse(timestamp).epochSecond,
        )
    }
}
