package impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import service.ServiceP01S01

/**
 * Part 1. Task 1.
 *
 * You need to write to the database all the rows received at the input of the saveToRepository() function.
 * The DBMS is optimized for parallel writing, but saving each record takes some time.
 * Use the `repository.save()` method to save a record.
 */
class CoroutinesP01S01(
    private val repository: ServiceP01S01.Repository
) {
    fun saveToRepository(rows: List<String>) {

        // Solution
        CoroutineScope(Dispatchers.IO).launch {
            rows.map { row ->
                launch { repository.save(row) }
            }.forEach { it.join() }
        }
    }
}
