package api

import org.w3c.xhr.XMLHttpRequest

/**
 * http://43.201.181.142:8000/swagger/
 */
class ApiClient(
    private val baseUrl: String = "http://43.201.181.142:8000",
) {
    fun getSchedule(
        scheduleId: Long,
        onSuccess: (Schedule?) -> Unit,
        onFailure: ((String) -> Unit)? = null,
    ) {
        val xmlHttpRequest = XMLHttpRequest()
        xmlHttpRequest.open("GET", "$baseUrl/schedule/$scheduleId/", true)
        xmlHttpRequest.send()
        xmlHttpRequest.onreadystatechange = {
            if (xmlHttpRequest.readyState == "4".toShort()) {
                if (xmlHttpRequest.status == "200".toShort()) {
                    val schedule = try {
                        Schedule.from(xmlHttpRequest.responseText)
                    } catch (e: Throwable) {
                        println("Failed to parse schedule. responseText: ${xmlHttpRequest.responseText}")
                        null
                    }
                    onSuccess(schedule)
                } else {
                    onFailure?.let { it(xmlHttpRequest.responseText) }
                        ?: println("Failed to get schedule. readySate: ${xmlHttpRequest.readyState}, status: ${xmlHttpRequest.status}, responseText: ${xmlHttpRequest.responseText}")
                }
            }
        }
    }

    fun createSchedule(
        name: String,
        startDate: String,
        endDate: String,
        startTime: String,
        endTime: String,
        onSuccess: (Schedule?) -> Unit,
        onFailure: ((String) -> Unit)? = null,
    ) {
        val scheduleCreateRequest = ScheduleCreateRequest(
            name = name,
            start_date = startDate,
            end_date = endDate,
            start_time = startTime,
            end_time = endTime,
        )

        val xmlHttpRequest = XMLHttpRequest()
        xmlHttpRequest.open("POST", "$baseUrl/schedule/", true)
        xmlHttpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8")
        xmlHttpRequest.setRequestHeader("Accept", "application/json")
        xmlHttpRequest.send(scheduleCreateRequest.toJson())
        xmlHttpRequest.onreadystatechange = {
            if (xmlHttpRequest.readyState == "4".toShort()) {
                if (xmlHttpRequest.status == "200".toShort()) {
                    val schedule = try {
                        Schedule.from(xmlHttpRequest.responseText)
                    } catch (e: Throwable) {
                        println("Failed to parse schedule. responseText: ${xmlHttpRequest.responseText}")
                        null
                    }
                    onSuccess(schedule)
                } else {
                    onFailure?.let { it(xmlHttpRequest.responseText) }
                        ?: println("Failed to create schedule. readySate: ${xmlHttpRequest.readyState}, status: ${xmlHttpRequest.status}, responseText: ${xmlHttpRequest.responseText}")
                }
            }
        }
    }
}
