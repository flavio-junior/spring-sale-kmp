package br.com.conding.tv.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal object AndroidAppContextProvider : KoinComponent {
    val context: Context by inject()
}

actual fun createDataStore(): DataStore<Preferences> {
    val appContext = AndroidAppContextProvider.context.applicationContext
    return buildPreferencesDataStore(
        producePath = { appContext.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath }
    )
}
