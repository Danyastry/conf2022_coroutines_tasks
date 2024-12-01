package impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import service.ServiceP01S02

/**
 * Part 1. Task 2.
 *
 * You need to read all the rows from the repository (database).
 * The DBMS is optimized for parallel reading, but reading each record takes some time.
 * Use the `repository.rowCount` property to get the number of records in the repository.
 * Use the `repository.read()` method to get the record by index.
 */
class CoroutinesP01S02(
    private val repository: ServiceP01S02.Repository
) {

    // Solution
    fun readFromRepository(): List<String> {
        return runBlocking {
            (0 until repository.rowCount).map { index ->
                async { repository.read(index) }
            }.awaitAll()
        }
    }
}
