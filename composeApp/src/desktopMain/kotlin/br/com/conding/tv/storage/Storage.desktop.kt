package br.com.conding.tv.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(): DataStore<Preferences> {
    return buildPreferencesDataStore(producePath = { DATA_STORE_FILE_NAME })
}