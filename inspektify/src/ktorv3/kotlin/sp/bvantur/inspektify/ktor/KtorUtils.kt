package sp.bvantur.inspektify.ktor

import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.toByteArray

internal object KtorUtils {
    suspend fun channelToByteArray(channel: ByteReadChannel): ByteArray = channel.toByteArray()
}
