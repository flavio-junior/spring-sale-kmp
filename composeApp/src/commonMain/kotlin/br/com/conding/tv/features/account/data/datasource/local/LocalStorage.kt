package br.com.conding.tv.features.account.data.datasource.local

import br.com.conding.tv.features.account.data.vo.TokenResponseVO

internal interface LocalStorage {
    suspend fun cleanToken()
    suspend fun getToken(): TokenResponseVO
    suspend fun saveToken(token: TokenResponseVO)
}
