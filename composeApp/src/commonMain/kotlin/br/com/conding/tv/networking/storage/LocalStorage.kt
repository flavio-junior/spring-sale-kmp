package br.com.conding.tv.networking.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalStorage(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun cleanToken() {
        dataStore.edit { settings ->
            settings.remove(PREFERENCES_USER)
            settings.remove(PREFERENCES_AUTHENTICATED)
            settings.remove(PREFERENCES_CREATED_AT)
            settings.remove(PREFERENCES_TYPE_ACCOUNT)
            settings.remove(PREFERENCES_EXPIRATION_DATE)
            settings.remove(PREFERENCES_ACCESS_TOKEN)
            settings.remove(PREFERENCES_REFRESH_TOKEN)
        }
    }

    suspend fun getToken(): TokenResponseVO {
        return dataStore.data.map { currentPreferences ->
            TokenResponseVO(
                user = currentPreferences[PREFERENCES_USER] ?: "",
                authenticated = currentPreferences[PREFERENCES_AUTHENTICATED] ?: false,
                created = currentPreferences[PREFERENCES_CREATED_AT] ?: "",
                type = currentPreferences[PREFERENCES_TYPE_ACCOUNT] ?: "",
                expiration = currentPreferences[PREFERENCES_EXPIRATION_DATE] ?: "",
                accessToken = currentPreferences[PREFERENCES_ACCESS_TOKEN] ?: "",
                refreshToken = currentPreferences[PREFERENCES_REFRESH_TOKEN] ?: ""
            )
        }.first()
    }

    suspend fun saveToken(
        token: TokenResponseVO
    ) {
        dataStore.edit { settings ->
            settings[PREFERENCES_USER] = token.user
            settings[PREFERENCES_AUTHENTICATED] = token.authenticated
            settings[PREFERENCES_CREATED_AT] = token.created
            settings[PREFERENCES_TYPE_ACCOUNT] = token.type
            settings[PREFERENCES_EXPIRATION_DATE] = token.expiration
            settings[PREFERENCES_ACCESS_TOKEN] = token.accessToken
            settings[PREFERENCES_REFRESH_TOKEN] = token.refreshToken
        }
    }

    companion object {
        private val PREFERENCES_USER = stringPreferencesKey(name = "user")
        private val PREFERENCES_AUTHENTICATED = booleanPreferencesKey(name = "authenticated")
        private val PREFERENCES_CREATED_AT = stringPreferencesKey(name = "created_at")
        private val PREFERENCES_TYPE_ACCOUNT = stringPreferencesKey(name = "type_account")
        private val PREFERENCES_EXPIRATION_DATE = stringPreferencesKey(name = "expiration_date")
        private val PREFERENCES_ACCESS_TOKEN = stringPreferencesKey(name = "access_token")
        private val PREFERENCES_REFRESH_TOKEN = stringPreferencesKey(name = "refresh_token")
    }
}
