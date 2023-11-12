data class AppContext(
    var page: Page,
) {
    fun navigateTo(page: Page): AppContext {
        return copyOf(
            page = page,
        )
    }

    private fun copyOf(
        page: Page? = null,
    ): AppContext {
        return AppContext(
            page = page ?: this.page,
        )
    }
}
