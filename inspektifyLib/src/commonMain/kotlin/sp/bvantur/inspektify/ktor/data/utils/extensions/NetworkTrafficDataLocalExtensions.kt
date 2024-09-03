package sp.bvantur.inspektify.ktor.data.utils.extensions

import inspektify.inspektifylib.generated.resources.Res
import inspektify.inspektifylib.generated.resources.img_http_icon
import inspektify.inspektifylib.generated.resources.img_https_icon
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import sp.bvantur.inspektify.NetworkTrafficDataLocal
import sp.bvantur.inspektify.ktor.domain.model.StatusCode
import sp.bvantur.inspektify.ktor.domain.model.StatusColor
import sp.bvantur.inspektify.ktor.domain.utils.ByteSizeUtils
import sp.bvantur.inspektify.ktor.domain.utils.DateTimeUtils

internal fun NetworkTrafficDataLocal.getPresentationStatusCode(): StatusCode {
    responseStatus ?: return StatusCode(statusCode = "???", statusColor = StatusColor.ORANGE)

    return StatusCode(
        statusCode = responseStatus.toString(),
        statusColor = if (responseStatus in 200L..299L) {
            StatusColor.GREEN
        } else {
            StatusColor.RED
        }
    )
}

internal fun NetworkTrafficDataLocal.getMethodWithPath(): String = "$method $path"

internal fun NetworkTrafficDataLocal.getHost(): String = host ?: ""

internal fun NetworkTrafficDataLocal.getTime(): String {
    requestTimestamp ?: return "???"

    val instant = Instant.fromEpochMilliseconds(requestTimestamp)
    val systemTimeZone = TimeZone.currentSystemDefault()
    val localDateTime = instant.toLocalDateTime(systemTimeZone)

    return DateTimeUtils.toTimeString(localDateTime)
}

internal fun NetworkTrafficDataLocal.getDuration(): String {
    if (responseTimestamp == null || requestTimestamp == null) return "???"

    return DateTimeUtils.toTextWithTimeUnit(responseTimestamp - requestTimestamp)
}

internal fun NetworkTrafficDataLocal.getSize(): String {
    var allSize = 0L
    if (responsePayloadSize != null) {
        allSize += responsePayloadSize
    }
    if (responseHeadersSize != null) {
        allSize += responseHeadersSize
    }
    if (requestPayloadSize != null) {
        allSize += requestPayloadSize
    }
    if (requestHeadersSize != null) {
        allSize += requestHeadersSize
    }

    return ByteSizeUtils.toTextWithByteUnit(allSize)
}

internal fun NetworkTrafficDataLocal.getHostImage(): DrawableResource = if (protocol == "https") {
    Res.drawable.img_https_icon
} else {
    Res.drawable.img_http_icon
}

internal fun NetworkTrafficDataLocal.getDate(): String {
    val instant = Instant.fromEpochMilliseconds(requestTimestamp ?: 0L)

    return DateTimeUtils.formatDate(instant.toLocalDateTime(TimeZone.currentSystemDefault()).date)
}

internal fun NetworkTrafficDataLocal.isCompleted(): Boolean = responseStatus != null
