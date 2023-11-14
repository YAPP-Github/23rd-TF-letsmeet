package api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Schedule(
    val id: Long,
    /**
     * maxLength: 256
     * minLength: 1
     */
    val name: String,
    val start_date: String,
    val end_date: String,
    val start_time: String,
    val end_time: String,
    val created_at: String,
    val updated_at: String,
    val selected_schedules: List<SelectedSchedule>,
) {
    companion object {
        fun from(json: String) = Json.decodeFromString(serializer(), json)
    }
}

@Serializable
data class SelectedSchedule(
    val id: Long,
    /**
     * schedule id 인듯
     */
    val schedule: Long,
    /**
     * maxLength: 256
     * minLength: 1
     */
    val username: String,
    val avail_abilities: List<AvailAbility>
)

@Serializable
data class AvailAbility(
    val id: Long,
    val start_time: String,
    val end_time: String,
    val selected_schedule: Long,
)
