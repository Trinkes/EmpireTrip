package com.redphoenix.empire.trip.network

import android.text.TextUtils
import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class LogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body()
        val logBuilder = StringBuilder()
        logBuilder.append(
            "<---------------------------BEGIN REQUEST---------------------------------->"
        )
        logBuilder.append("\n")
        logBuilder.append("Request encoded url: ")
            .append(request.method())
            .append(" ")
            .append(
                requestPath(request.url())
            )
        logBuilder.append("\n")
        val decodeUrl = requestDecodedPath(request.url())
        if (!TextUtils.isEmpty(decodeUrl)) {
            logBuilder.append("Request decoded url: ")
                .append(request.method())
                .append(" ")
                .append(decodeUrl)
        }

        var headers = request.headers()
        logBuilder.append("\n=============== Headers ===============\n")
        for (i in headers.size() - 1 downTo -1 + 1) {
            logBuilder.append(headers.name(i))
                .append(" : ")
                .append(headers.get(headers.name(i)))
                .append("\n")
        }
        logBuilder.append("\n=============== END Headers ===============\n")

        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            val contentType = requestBody.contentType()
            contentType?.charset(UTF8)

            logBuilder.append(
                buffer.readString(
                    UTF8
                )
            )
        }
        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        logBuilder.append("\n")
        logBuilder.append("Response timeout: ")
            .append(tookMs)
            .append("ms")
        logBuilder.append("\n")
        logBuilder.append("Response message: ")
            .append(response.message())
        logBuilder.append("\n")
        logBuilder.append("Response code: ")
            .append(response.code())

        if (responseBody != null) {
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            var charset: Charset? = null
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(
                    UTF8
                )
            }

            if (charset == null) {
                charset = UTF8
            }

            if (responseBody.contentLength() != 0L) {
                logBuilder.append("\n")
                logBuilder.append("Response body: \n")
                    .append(
                        buffer.clone()
                            .readString(charset!!)
                    )
            }
        }
        headers = response.headers()
        logBuilder.append("\n=============== Headers ===============\n")
        for (i in headers.size() - 1 downTo -1 + 1) {
            logBuilder.append(headers.name(i))
                .append(" : ")
                .append(headers.get(headers.name(i)))
                .append("\n")
        }
        logBuilder.append("\n=============== END Headers ===============\n")

        logBuilder.append("\n")
        logBuilder.append(
            "<-----------------------------END REQUEST--------------------------------->"
        )
        logBuilder.append("\n\n\n")
        Log.d(TAG, logBuilder.toString())
        return response
    }

    private fun requestDecodedPath(url: HttpUrl): String? {
        try {
            val path = URLDecoder.decode(url.encodedPath(), "UTF-8")
            val query = URLDecoder.decode(url.encodedQuery()!!, "UTF-8")
            return url.scheme() + "://" + url.host() + if (query != null) path + '?'.toString() + query else path
        } catch (ex: Exception) {
            /* Quality */
        }

        return null
    }

    companion object {
        private val TAG = "HTTP_TRACE"
        private val UTF8 = Charset.forName("UTF-8")

        private fun requestPath(url: HttpUrl): String {
            val path = url.encodedPath()
            val query = url.encodedQuery()
            return url.scheme() + "://" + url.host() + if (query != null) path + '?'.toString() + query else path
        }
    }
}