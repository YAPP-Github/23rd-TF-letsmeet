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
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
    val createdAt: String,
    val updatedAt: String,
    val selectedSchedules: List<SelectedSchedule>,
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
    val scheduleId: Long,
    /**
     * maxLength: 256
     * minLength: 1
     */
    val username: String,
    val availAbilities: List<AvailAbility>
)

@Serializable
data class AvailAbility(
    val id: Long,
    val startTime: String,
    val endTime: String,
    val selectedScheduleId: Long,
)
