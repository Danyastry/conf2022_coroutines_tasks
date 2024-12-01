package impl

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import service.ServiceP03S03

/**
 * Part 3. Task 3.
 *
 * In this task you need to read records from one database and write them to another.
 * Reading and writing to channels can be performed in parallel mode.
 * The degree of parallelism is specified by the `readParallelism` (for reading) and `writeParallelism` (for writing) parameters.
 */
object CoroutinesP03S03 {
    suspend fun pumpDataBase(
        readParallelism: Int,
        writeParallelism: Int,
        reader: ServiceP03S03.DataBaseReader,
        writer: ServiceP03S03.DataBaseWriter
    ) {

        // Solution
        val channel = Channel<ServiceP03S03.Record>()

        withContext(Dispatchers.IO) {
            repeat(writeParallelism) {
                launch {
                    channel.consumeEach { record ->
                        writer.write(record)
                    }
                }
            }
            coroutineScope {
                repeat(readParallelism) {
                    launch {
                        while (reader.hasNextRecord()) {
                            reader.readNextRecordOrNull()?.let { record ->
                                channel.send(record)
                            }
                        }
                    }
                }
            }
            channel.close()
        }
    }
}
