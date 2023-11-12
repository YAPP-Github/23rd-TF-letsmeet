import kotlin.js.Date

fun Date.getFormattedDate() = "${getFullYear()}-${(getMonth() + 1)}-${getDate()}"
