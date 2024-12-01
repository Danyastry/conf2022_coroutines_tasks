package impl

import kotlinx.coroutines.*

/**
 * Part 6. Task 2.
 *
 * In this assignment, you need to handle exceptions raised in child coroutines.
 * Use the `onException` callback to handle the exception:
 * 1. Pass the message from the first exception that occurred in the `message` parameter.
 * 2. All other exceptions are in the `uppressed` list.
 * Hint: try to apply an exception handler.
 */
object CoroutinesP06S02 {
    suspend fun safeLaunchAndJoin(
        onException: (message: String, suppressed: List<String>) -> Unit,
        body: suspend CoroutineScope.() -> Job
    ) {

        // Solution
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            val message = throwable.message.toString()
            val suppressed = throwable.suppressed.map { it.message.toString() }
            onException(message, suppressed)
        }
        val job = CoroutineScope(coroutineExceptionHandler).launch {
            body()
        }
        job.join()
    }
}
