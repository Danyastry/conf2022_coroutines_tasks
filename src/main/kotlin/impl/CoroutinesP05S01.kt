package impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

/**
 * Part 5. Task 1.
 *
 * You have two channels with messages.
 * You need to return a new channel for logging and send messages from both channels to it when they are received.
 * You need to log messages until one of the incoming channels is closed.
 */
object CoroutinesP05S01 {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun CoroutineScope.logMessages(
        messageChannel1: ReceiveChannel<String>,
        messageChannel2: ReceiveChannel<String>
    ): ReceiveChannel<String> {

        // Solution
        return produce {
            var channelIsClosed = false
            while (!channelIsClosed) {
                val message = select {
                    messageChannel1.onReceiveCatching { it.getOrNull() }
                    messageChannel2.onReceiveCatching { it.getOrNull() }
                }
                if (message != null) send(message) else channelIsClosed = true
            }
        }
    }
}
