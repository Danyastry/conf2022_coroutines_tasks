package impl

import kotlinx.coroutines.flow.*
import service.ServiceP04S03

/**
 * Part 4. Task 3.
 *
 * You receive three streams as input: identifiers, names, and phone numbers.
 * Combine them into a single stream so that each output item contains a string to be written to a CSV file.
 * The CSV header is `id,name,phone`.
 */
object CoroutinesP04S03 {
    suspend fun writeLeads(
        idFlow: Flow<Int>,
        nameFlow: Flow<String>,
        phoneFlow: Flow<String>,
        writer: ServiceP04S03.Writer
    ) {

        // Solution 1
        combine(idFlow, nameFlow, phoneFlow) { id, name, phone ->
            "$id,$name,$phone"
        }.collect { row ->
            writer.write(row)
        }

        // Solution 2
        idFlow.zip(nameFlow) { id, name ->
            Pair(id,name)
        }.zip(phoneFlow) { (id, name), phone ->
            "$id,$name,$phone"
        }.collect { row ->
            writer.write(row)
        }

    }
}
