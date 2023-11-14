
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import api.ApiClient
import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLInputElement
import kotlin.js.Date

fun main() {
    var appContext: AppContext by mutableStateOf(
        AppContext(
            page = Page.HOME,
        )
    )

    // FIXME: 주소 바꿔도 index.html 민 렌더링하는 방법을 몰라서 주소 사용 안함.
    renderComposable(rootElementId = "root") {
        when (appContext.page) {
            Page.HOME -> renderHomePage { appContext = appContext.navigateTo(it) }
            Page.CREATE -> renderCreatePage { appContext = appContext.navigateTo(it) }
            Page.DETAIL -> renderDetailPage { appContext = appContext.navigateTo(it) }
        }
    }
}

@Composable
fun renderHomePage(callback: (Page) -> Unit) {
    Div {
        H1 {
            Text("When 2 Yapp")
        }
        Button(attrs = {
            onClick {
                callback(Page.CREATE)
            }
        }) {
            Text("약속 만들기")
        }
    }
}

@Composable
fun renderCreatePage(callback: (Page) -> Unit) {
    val now = Date(Date.now())

    Div {
        H1 {
            Text("약속 만들기")
        }
        Form {
            Input(InputType.Text, attrs = {
                id("name")
                placeholder("약속 제목")
            })
            Input(InputType.Number, attrs = {
                id("startTime")
                placeholder("약속 시작 시각")
            })
            Input(InputType.Number, attrs = {
                id("endTime")
                placeholder("약속 종료 시각")
            })
            Input(InputType.Date, attrs = {
                id("startDate")
                placeholder("약속 시작 날짜")
                defaultValue(now.getFormattedDate())
            })
            Input(InputType.Date, attrs = {
                id("endDate")
                placeholder("약속 종료 날짜")
                defaultValue(now.getFormattedDate())
            })
            Input(InputType.Button, attrs = {
                value("약속 만들기")
                onClick {
                    ApiClient().createSchedule(
                        name = (document.getElementById("name") as HTMLInputElement).value,
                        startTime = "${(document.getElementById("startTime") as HTMLInputElement).value}:00:00",
                        endTime = "${(document.getElementById("endTime") as HTMLInputElement).value}:00:00",
                        startDate = (document.getElementById("startDate") as HTMLInputElement).value,
                        endDate = (document.getElementById("endDate") as HTMLInputElement).value,
                        onSuccess = {
                            println(it)
                        },
                    )
                }
            })
        }
        Button(attrs = {
            onClick {
                callback(Page.DETAIL)
            }
        }) {
            Text("to Detail")
        }
    }
}

@Composable
fun renderDetailPage(callback: (Page) -> Unit) {
    val appointment = Appointment(
        title = "약속 제목",
        startedHour = 10,
        endedHour = 22,
        startDate = Date(2023, 11, 12),
        endDate = Date(2023, 11, 18),
    )
    val schedule = ApiClient().getSchedule(
        3L,
        onSuccess = {
            println("success to get schedule. schedule: ${it.toString()}")
        },
    )
    Div {
        H1 {
            Text("약속 보기")
        }

        H2 {
            Text(appointment.title)
        }

        renderTimeTable(appointment)

        Button(attrs = {
            onClick {
                callback(Page.HOME)
            }
        }) {
            Text("to Home")
        }
    }
}

@Composable
fun renderTimeTable(
    appointment: Appointment,
) {
    Div(attrs = {
        id("time-table")
    }) {
        Table {
            Thead {
                Tr {
                    appointment.dateRange.forEach { date ->
                        run {
                            Th {
                                Text("$date")
                            }
                        }
                    }
                }
            }
            Tbody {
                appointment.hourRange.forEach { hour ->
                    run {
                        Tr {
                            appointment.dateRange.forEach { _ ->
                                run {
                                    Td {
                                        Text("$hour")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
