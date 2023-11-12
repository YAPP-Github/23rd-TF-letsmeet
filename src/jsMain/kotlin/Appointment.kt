import kotlin.js.Date

class Appointment(
    /**
     * 제목
     */
    val title: String,
    /**
     * 시작 시각
     */
    val startedHour: Int,
    /**
     * 종료 시각
     */
    val endedHour: Int,
    /**
     * 시작 날짜
     */
    val startDate: Date,
    /**
     * 종료 날짜
     */
    val endDate: Date,
) {

    val hourRange: IntRange = startedHour..endedHour
    val dateRange: IntRange = startDate.getDate()..endDate.getDate()
}
