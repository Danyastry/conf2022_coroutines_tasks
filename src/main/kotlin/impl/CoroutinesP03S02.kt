package impl

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

/**
 * Part 3. Task 2.
 *
 * Now - the other way around, send all the elements of the list into the channel one by one.
 * Each item must be converted to a string using the `body()` function.
 */
object CoroutinesP03S02 {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun CoroutineScope.listToChannel(
        list: List<Int>,
        body: suspend (Int) -> String
    ): ReceiveChannel<String> {

        // Solution
        return produce {
            list.forEach { element ->
                send(body(element))
            }
        }
    }
}
