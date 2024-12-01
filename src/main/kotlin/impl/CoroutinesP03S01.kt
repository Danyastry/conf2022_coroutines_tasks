@file:Suppress("UNREACHABLE_CODE")

package impl

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach

/**
 * Part 3. Task 1.
 *
 * Try to write your own function to convert a channel into a list.
 * Each item must be converted to a string using the `body()` function.
 */
object CoroutinesP03S01 {
    suspend fun channelToList(
        channel: ReceiveChannel<Int>,
        body: suspend (Int) -> String
    ): List<String> {

        // Solution 1
        val list = mutableListOf<String>()
        for (element in channel) {
            list.add(body(element))
        }
        return list

        // Solution 2
        return buildList {
            channel.consumeEach { element ->
                add(body(element))
            }
        }
    }

}
