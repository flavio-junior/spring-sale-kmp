package br.com.conding.tv.features.account.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalStorageImpl(
    private val dataStore: DataStore<Preferences>
) : LocalStorage {

    override suspend fun cleanToken() {
        dataStore.edit { settings ->
            settings.remove(PREFERENCES_EXPIRATION_DATE)
            settings.remove(PREFERENCES_ACCESS_TOKEN)
            settings.remove(PREFERENCES_REFRESH_TOKEN)
        }
    }

    override suspend fun getToken(): TokenResponseVO {
        return dataStore.data.map { currentPreferences ->
            TokenResponseVO(
                expiration = currentPreferences[PREFERENCES_EXPIRATION_DATE] ?: "",
                accessToken = currentPreferences[PREFERENCES_ACCESS_TOKEN] ?: "",
                refreshToken = currentPreferences[PREFERENCES_REFRESH_TOKEN] ?: ""
            )
        }.first()
    }

    override suspend fun saveToken(
        token: TokenResponseVO
    ) {
        dataStore.edit { settings ->
            settings[PREFERENCES_EXPIRATION_DATE] = token.expiration
            settings[PREFERENCES_ACCESS_TOKEN] = token.accessToken
            settings[PREFERENCES_REFRESH_TOKEN] = token.refreshToken
        }
    }

    companion object {
        private val PREFERENCES_EXPIRATION_DATE = stringPreferencesKey(name = "expiration_date")
        private val PREFERENCES_ACCESS_TOKEN = stringPreferencesKey(name = "access_token")
        private val PREFERENCES_REFRESH_TOKEN = stringPreferencesKey(name = "refresh_token")
    }
}
