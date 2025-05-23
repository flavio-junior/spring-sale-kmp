package br.com.conding.tv

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform