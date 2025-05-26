package br.com.conding.tv.resources

fun checkNameIsNull(name: String): Boolean {
    return (name.isEmpty())
}

fun checkPriceIsNull(price: Double): Boolean {
    return (price == 0.0)
}

fun checkPriceIsEqualsZero(price: Double): Boolean {
    return (price == 0.0)
}

fun validateEmail(email: String): Boolean {
    val emailRegex = Regex(
        pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    )
    return emailRegex.matches(email)
}

fun CharSequence.isBlankAndEmpty(): Boolean {
    return isBlank() && isEmpty()
}

fun CharSequence.isNotBlankAndEmpty(): Boolean {
    return isNotBlank() && isNotEmpty()
}
