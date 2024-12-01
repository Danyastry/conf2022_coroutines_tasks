package impl

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import service.ServiceP02S01

/**
 * Part 2. Task 1.
 *
 * You have a `Counter` class that is not thread-safe.
 * You need to modify the function so that calls to `body(i)` remain parallel,
 * so that `counter` returns the correct result.
 */
object CoroutinesP02S01 {
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun executeAndSum(
        counter: ServiceP02S01.Counter,
        times: Int,
        body: suspend (i: Int) -> Int
    ) {

        // Solution 1
        val mutex = Mutex()
        coroutineScope {
            repeat(times) { i ->
                launch(Dispatchers.IO) {
                    mutex.withLock {
                        counter.add(body(i))
                    }
                }
            }
        }

        // Solution 2
        newSingleThreadContext("Single Thread").use { context ->
            withContext(context) {
                repeat(times) { i ->
                    counter.add(body(i))
                }
            }
        }

        // Solution 3
        newSingleThreadContext("").use { context ->
            withContext(context) {
                repeat(times) { i ->
                    mutex.withLock {
                        counter.add(body(i))
                    }
                }
            }
        }
    }
}
