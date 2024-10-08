package sp.bvantur.inspektify.ktor.client.data

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.contentType
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import sp.bvantur.inspektify.ktor.client.data.model.NetworkTraffic
import sp.bvantur.inspektify.ktor.core.data.utils.NetworkTrafficDataUtils

internal class InspektifyResponseHandler {

    suspend fun handleResponse(response: HttpResponse, networkTraffic: NetworkTraffic): NetworkTraffic {
        val timestamp = response.responseTime.timestamp
        val status = response.status.value
        val description = response.status.description
        val headers = response.headers
        val headersSize = NetworkTrafficDataUtils.calculateHeadersSize(headers)
        val payload = response.bodyAsText(fallbackCharset = Charsets.UTF_8)

        return networkTraffic.copy(
            responseTimestamp = timestamp,
            responseStatus = status,
            responseContentType = response.contentType()?.contentType,
            responseStatusDescription = description,
            responseHeaders = headers.entries(),
            responsePayload = payload,
            responsePayloadSize = payload.toByteArray().size.toLong(),
            responseHeadersSize = headersSize,
            tookDurationInMs = timestamp - (networkTraffic.requestTimestamp ?: 0),
        )
    }
}
