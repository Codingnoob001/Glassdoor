/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.data.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.glassdoor.intern.data.mapper.HeaderInfoMapper
import com.glassdoor.intern.data.source.InfoApi
import com.glassdoor.intern.domain.model.HeaderInfo
import com.glassdoor.intern.domain.repository.InfoRepository
import timber.log.Timber
import javax.inject.Inject


internal class InfoRepositoryImpl @Inject constructor(
    private val infoApi: InfoApi,
    private val headerInfoMapper: HeaderInfoMapper
) : InfoRepository {
    private var lastSuccessfulHeaderInfo: HeaderInfo? = null

    override suspend fun getHeaderInfo(): Result<HeaderInfo, Throwable> =
        try {
            with(infoApi.getInfo()) {
                when {
                    header != null -> {
                        val result = headerInfoMapper.toDomain(header, items)
                        lastSuccessfulHeaderInfo = result
                        Ok(result)
                    }
                    error != null -> Err(Throwable(error))
                    else -> Err(Throwable("unexpected error occurred"))
                }
            }
        } catch (throwable: Throwable) {
            Timber.e(throwable, "InfoRepositoryImpl")
            lastSuccessfulHeaderInfo?.let { Ok(it) } ?: Err(throwable)
        }
}
