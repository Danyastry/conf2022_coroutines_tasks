package impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.zip
import service.ServiceP04S04

/**
 * Part 4. Task 4.
 *
 * You receive two streams in which values from different sensors are transmitted: air temperature and humidity.
 * You need to combine these two streams in such a way that when a new value appears in either of them.
 * both values are rendered in the user interface (function `renderer.render()`).
 * Note that sensors are unstable and may sometimes return `null` values - such values do not need to be rendered.
 */
object CoroutinesP04S04 {
    suspend fun renderMeasurements(
        temperatureFlow: Flow<Double?>,
        humidityFlow: Flow<Int?>,
        renderer: ServiceP04S04.Renderer
    ) {

        // Solution 1
        temperatureFlow.combine(humidityFlow) { temperature, humidity ->
            if (temperature != null && humidity != null) {
                ServiceP04S04.Measurements(temperature, humidity)
            } else {
                null
            }
        }.filterNotNull().collect { measurements ->
            renderer.render(measurements)
        }

        // Solution 2
        temperatureFlow.filterNotNull().combine(
            humidityFlow.filterNotNull()) { temperature, humidity ->
                ServiceP04S04.Measurements(temperature, humidity)
            }.collect { measurements ->
                renderer.render(measurements)
            }

    }
}
