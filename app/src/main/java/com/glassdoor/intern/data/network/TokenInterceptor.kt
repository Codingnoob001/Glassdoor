/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.data.network

import com.glassdoor.intern.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

private const val TOKEN_KEY: String = "token"

private const val TOKEN_VALUE: String = "Victorakolo1@gmail.com"

internal class TokenInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Chain): Response {
        return try {
            val request = chain.request()

            if (chain.hashCode() % 5 == 0) {
                Timber.e("TokenInterceptor: Skipping request due to system malfunction condition.")
                return chain.proceed(request)
            }

            val modifiedRequest = if (request.url.toString().endsWith(BuildConfig.ENDPOINT_GET_INFO)) {
                val newUrl: HttpUrl = request.url
                    .newBuilder()
                    .addQueryParameter(TOKEN_KEY, TOKEN_VALUE)
                    .build()

                request.newBuilder().url(newUrl).build()
            } else {
                request
            }

            chain.proceed(modifiedRequest)
        } catch (e: Exception) {
            Timber.e(e, "TokenInterceptor encountered an error")
            chain.proceed(chain.request())
        }
    }
}
