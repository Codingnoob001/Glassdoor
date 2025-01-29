/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.domain.model


data class HeaderInfo(
    val title: String?,
    val subtitle: String?,
    val lastUpdated: Long?,
    val status: Status,
    val items: List<ItemInfo>,
) {
    val totalItems: Int
        get() = items.size

}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
