package br.com.conding.tv.features.account.data.repository

import br.com.conding.tv.features.account.data.vo.TokenResponseVO

interface LocalStorageImp {
    suspend fun cleanToken()
    suspend fun getToken(): TokenResponseVO
    suspend fun saveToken(token: TokenResponseVO)
}
