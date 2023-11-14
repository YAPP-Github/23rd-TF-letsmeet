package api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ScheduleCreateRequest(
    /**
     * maxLength: 256
     * minLength: 1
     */
    val name: String,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
) {
    fun toJson() = Json.encodeToString(serializer(), this)
}
