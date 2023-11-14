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
    val start_date: String,
    val end_date: String,
    val start_time: String,
    val end_time: String,
) {
    fun toJson() = Json.encodeToString(serializer(), this)
}
