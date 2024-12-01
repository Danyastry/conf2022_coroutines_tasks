@file:Suppress("UNREACHABLE_CODE")

package impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger

/**
 * Part 2. Task 2.
 *
 * Now you can implement your own thread-safe counter.
 * Which class is better to use?
 */
object CoroutinesP02S02 {
    suspend fun executeAndSum(times: Int, body: suspend (i: Int) -> Int): Int {

        // Solution 1
        val counter = AtomicInteger(0)
        coroutineScope {
            repeat(times) { i ->
                launch {
                    counter.addAndGet(body(i))
                }
            }
        }
        return counter.get()

        // Solution 2
        var counter1 = 0
        val mutex = Mutex()
        coroutineScope {
            repeat(times) { i ->
                launch {
                    mutex.withLock {
                        counter1 += body(i)
                    }
                }
            }
        }
        return counter
    }
}