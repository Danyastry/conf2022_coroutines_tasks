package impl

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.selects.select

/**
 * Part 5. Task 2.
 *
 * In this task you need to process all the messages from the `messageChannel` channel
 * and send them to the `processChannel` or `logChannel` channel.
 * In doing so, the main `processChannel` channel processes messages rather slowly,
 * so all messages received while `processChannel` is busy should be sent to `logChannel`.
 */
object CoroutinesP05S02 {
    suspend fun processMessages(
        messageChannel: ReceiveChannel<String>,
        processChannel: SendChannel<String>,
        logChannel: SendChannel<String>
    ) {

        // Solution
        messageChannel.consumeEach { message ->
            select {
                processChannel.onSend(message) {
                    // Message sent successfully to processChannel
                }
                logChannel.onSend(message) {
                    // Message sent successfully to logChannel
                }
            }
        }
    }
}
