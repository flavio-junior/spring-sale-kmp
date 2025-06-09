package br.com.conding.tv.resources

internal fun checkNameIsNull(name: String): Boolean {
    return (name.isEmpty())
}

internal fun checkPriceIsNull(price: Double): Boolean {
    return (price == 0.0)
}

internal fun checkPriceIsEqualsZero(price: Double): Boolean {
    return (price == 0.0)
}

internal fun validateEmail(email: String): Boolean {
    val emailRegex = Regex(
        pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    )
    return emailRegex.matches(email)
}

internal fun CharSequence.isBlankAndEmpty(): Boolean {
    return isBlank() && isEmpty()
}

internal fun CharSequence.isNotBlankAndEmpty(): Boolean {
    return isNotBlank() && isNotEmpty()
}
