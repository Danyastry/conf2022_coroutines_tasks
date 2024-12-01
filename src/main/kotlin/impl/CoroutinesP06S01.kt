package impl

import kotlinx.coroutines.*

/**
 * Part 6. Task 1.
 *
 * Implement a “safe” coroutine startup function that will call colbacks:
 * `onCancel` - in case the coroutine is canceled
 * `onException` - in case of an exception inside the coroutine
 */
object CoroutinesP06S01 {
    fun CoroutineScope.safeLaunch(
        onCancel: (message: String) -> Unit = {},
        onException: (message: String) -> Unit = {},
        body: suspend () -> Unit
    ): Job {

        // Solution
        return launch {
            try {
                body()
            } catch (e: CancellationException) {
                onCancel(e.message.toString())
            } catch (e: Throwable) {
                onException(e.message.toString())
            }
        }
    }
}
