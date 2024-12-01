package impl

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import service.ServiceP04S02

/**
 * Part 4. Task 2.
 *
 * You are to convert the resulting list of strings into a list of phones and “save” each of them.
 * 1. The input string can contain multiple phone numbers in any format, separated by `,` or `;`.
 * 2. Each phone number must be normalized and saved (call the `writer.write()` method for it).
 * 3. Only the cell phone number in `9xxxxxxxxxx` format (10 digits) should be saved.
 * 4. The `limit` parameter is used to limit the number of received phones.
 */
object CoroutinesP04S02 {
    @OptIn(FlowPreview::class)
    suspend fun filterPhones(
        flow: Flow<String>,
        writer: ServiceP04S02.Writer,
        limit: Int
    ) {

        // Solution
        flow.flatMapConcat { line ->
            line.split("[,;]").asFlow()
        }.map { phone ->
            phone.filter { it.isDigit() }
        }.filter { normPhone ->
            normPhone.length == 10 && normPhone.startsWith("9")
        }.take(limit).collect { validPhone ->
            writer.write(validPhone)
        }

    }
}
