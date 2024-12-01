package impl

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

/**
 * Part 1. Task 3.
 *
 * You need to call the `body()` function passed as a parameter to `exec()`, but with one condition:
 * `body()` must not take more than 1 second to execute.
 * If `body()` takes longer to execute, you should return the string “Too long body execution”,
 * without waiting for the execution to complete.
 */
object CoroutinesP01S03 {
    private const val TIMEOUT_MS = 1000L
    private const val TIMEOUT_MESSAGE = "Too long body execution"

    suspend fun exec(body: suspend () -> String, timeout: Long = TIMEOUT_MS): String {

        // Solution
        return try {
            withTimeout(timeout) {
                body()
            }
        } catch (e: TimeoutCancellationException) {
            TIMEOUT_MESSAGE
        }
    }
}
