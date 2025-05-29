package br.com.conding.tv.networking.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import br.com.conding.tv.networking.di.DATA_STORE_FILE_NAME
import okio.Path.Companion.toPath

actual fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(produceFile = { DATA_STORE_FILE_NAME.toPath() })
}