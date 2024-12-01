package impl

import kotlinx.coroutines.*
import service.ServiceP01S06

/**
 * Part 1. Task 6.
 *
 * You need to modify the logic of the `execute()` function so that,
 * so that `log.logAfter()` is called with the same context set inside `body()`
 */
object CoroutinesP01S06 {
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun execute(
        log: ServiceP01S06.Log,
        body: suspend () -> Unit
    ) {

        // Solution 1
        val context = newSingleThreadContext("context1")
        log.logBefore()

        coroutineScope {
            withContext(context) {
                body()
            }
            log.logAfter()
        }

        // Solution 2
        withContext(Dispatchers.Unconfined){
            log.logBefore()
            body()
            log.logAfter()
        }
    }
}
