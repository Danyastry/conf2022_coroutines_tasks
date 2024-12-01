package impl

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

/**
 * Part 1. Task 4.
 *
 * You need to implement a function that calls the lambdas passed to it in different threads.
 * The names of the threads must match the received values of `thread1Name` and `thread2Name`:
 * 1. the `prepare()` function must be run in a thread named `thread1Name`
 * 2. the `getQuery()` function must be run in a thread named `thread2Name`.
 * 3. the `execute()` function must be run in a thread named `thread1Name`.
 * Function calls must be executed in the following order:
 * 1. `prepare()` can be executed in parallel with `getQuery()` and `execute()`
 * 2. `execute()` must be called after `getQuery()`, and receives the result of `getQuery()` execution as input.
 */
object CoroutinesP01S04 {
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun exec(
        thread1Name: String,
        thread2Name: String,
        prepare: suspend () -> Unit,
        getQuery: suspend () -> String,
        execute: suspend (query: String) -> Unit
    ) {

        // Solution
        val context1 = newSingleThreadContext(thread1Name)
        val context2 = newSingleThreadContext(thread2Name)

        try {
            coroutineScope {
                launch(context1) { prepare() }
                val query = withContext(context2) {
                    getQuery()
                }
                withContext(context1) { execute(query) }
            }
        } finally {
            context1.close()
            context2.close()
        }
    }
}
