package br.com.conding.tv.data.repository.local

import br.com.conding.tv.features.account.data.repository.LocalStorageImp
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import java.util.prefs.Preferences

class DesktopLocalStorage : LocalStorageImp {
    private val preferences = Preferences.userRoot().node("token_storage")

    override suspend fun cleanToken() {
        preferences.apply {
            remove("user")
            remove("authenticated")
            remove("created")
            remove("type_account")
            remove("expiration")
            remove("access_token")
            remove("refresh_token")
            flush()
        }
    }

    override suspend fun getToken(): TokenResponseVO {
        return TokenResponseVO(
            user = preferences.get("user", ""),
            authenticated = preferences.getBoolean("authenticated", false),
            created = preferences.get("created", ""),
            type = preferences.get("type_account", ""),
            expiration = preferences.get("expiration", ""),
            accessToken = preferences.get("access_token", ""),
            refreshToken = preferences.get("refresh_token", "")
        )
    }

    override suspend fun saveToken(token: TokenResponseVO) {
        preferences.apply {
            put("user", token.user)
            putBoolean("authenticated", token.authenticated)
            put("created", token.created)
            put("type_account", token.type)
            put("expiration", token.expiration)
            put("access_token", token.accessToken)
            put("refresh_token", token.refreshToken)
            flush()
        }
    }
}
