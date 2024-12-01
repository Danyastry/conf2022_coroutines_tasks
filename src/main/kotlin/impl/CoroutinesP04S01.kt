package impl

import kotlinx.coroutines.flow.flow
import service.ServiceP04S01

/**
 * Part 4. Task 1.
 *
 * Now let's deal with asynchronous threads.
 * First, try to form an asynchronous flow from an `iterator' that has the `hasNext()` and `next()` methods
 * The resulting stream should be passed to the `writeFromFlow()` method of the iterator.
 */
object CoroutinesP04S01 {
    suspend fun processItemsList(
        reader: ServiceP04S01.Reader,
        writer: ServiceP04S01.Writer
    ) {

        // Solution
        val dFlow = flow {
            while (reader.hasNext()) {
                emit(reader.next())
            }
        }
        writer.writeFromFlow(dFlow)
    }
}
