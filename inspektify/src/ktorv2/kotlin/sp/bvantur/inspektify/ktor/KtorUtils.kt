package sp.bvantur.inspektify.ktor

import io.ktor.util.toByteArray
import io.ktor.utils.io.ByteReadChannel

object KtorUtils {
    suspend fun channelToByteArray(channel: ByteReadChannel): ByteArray {
        return channel.toByteArray()
    }
}
