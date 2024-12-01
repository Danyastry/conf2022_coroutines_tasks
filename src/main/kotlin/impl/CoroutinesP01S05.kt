package impl

import kotlinx.coroutines.*

/**
 * Part 1. Task 5.
 *
 * The `executeDataBaseRequest()` function is designed to execute parallel database queries
 * With a degree of `parallelism`.
 * It is necessary to ensure that at least 64 queries can be executed simultaneously
 * even if the thread inside the lambda is blocked.
 */
object CoroutinesP01S05 {
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun executeDataBaseRequest(
        parallelism: Int,
        body: suspend () -> Unit
    ) {

        // Solution 1
        val context = newFixedThreadPoolContext(parallelism, "dbPool")
        context.use { context ->
            coroutineScope {
                repeat(parallelism) {
                    launch(context) {
                        body()
                    }
                }
            }
        }

        // Solution 2
        withContext(Dispatchers.IO) {
            repeat(parallelism) {
               launch { body() }
            }
        }
    }
}
