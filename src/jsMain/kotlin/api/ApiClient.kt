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
        with(XMLHttpRequest()) {
            open("GET", "$baseUrl/schedule/$scheduleId/", true)
            send()
            onreadystatechange = {
                if (readyState == "4".toShort()) {
                    if (status == "200".toShort()) {
                        val schedule = try {
                            Schedule.from(responseText)
                        } catch (e: Throwable) {
                            println("Failed to parse schedule. responseText: $responseText")
                            null
                        }
                        onSuccess(schedule)
                    } else {
                        onFailure?.let { it(responseText) }
                            ?: println("Failed to get schedule. readySate: ${readyState}, status: ${status}, responseText: $responseText")
                    }
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
            startDate = startDate,
            endDate = endDate,
            startTime = startTime,
            endTime = endTime,
        )

        with(XMLHttpRequest()) {
            open("POST", "$baseUrl/schedule/", true)
            setRequestHeader("Content-Type", "application/json;charset=UTF-8")
            setRequestHeader("Accept", "application/json")
            send(scheduleCreateRequest.toJson())
            onreadystatechange = {
                if (readyState == "4".toShort()) {
                    if (status == "201".toShort()) {
                        val schedule = try {
                            Schedule.from(responseText)
                        } catch (e: Throwable) {
                            println("Failed to parse schedule. responseText: $responseText")
                            null
                        }
                        onSuccess(schedule)
                    } else {
                        onFailure?.let { it(responseText) }
                            ?: println("Failed to create schedule. readySate: ${readyState}, status: ${status}, responseText: $responseText")
                    }
                }
            }
        }
    }
}
