package impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Part 2. Task 3.
 *
 * What if you need not just to increment a counter value,
 * but to perform more complex transformations of a shared resource, such as assembling a string?
 */
object CoroutinesP02S03 {
    suspend fun executeAndConcatenate(times: Int, body: suspend (i: Int) -> String): String {

        // Solution
        val accumulator = StringBuilder()
        val mutex = Mutex()
        coroutineScope {
            repeat(times) { i ->
                launch {
                    val value = body(i)
                    mutex.withLock {
                        accumulator.append(value)
                    }
                }
            }
        }
        return accumulator.toString()
    }
}
