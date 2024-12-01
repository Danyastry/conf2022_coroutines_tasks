package impl

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.selects.select

/**
 * Part 5. Task 3.
 *
 * You get a list of deferred objects.
 * You need to return the first `count` of results from those coroutines that will finish their execution first.
 * You don't need to wait for the remaining coroutines to execute!
 */
object CoroutinesP05S03 {
    suspend fun takeFirstMessages(
        deferredList: List<Deferred<String>>,
        count: Int
    ): List<String> {

        // Solution
        val list = mutableListOf<String>() // or via buildList {}
        repeat(count) {
            select {
                deferredList.forEach { deferred -> // or .filter { it.isCompleted }
                    deferred.onAwait {
                        list.add(it)
                    }
                }
            }
        }
        return list
    }
}
