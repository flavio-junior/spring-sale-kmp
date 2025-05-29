package br.com.conding.tv.networking.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import br.com.conding.tv.networking.di.DATA_STORE_FILE_NAME
import okio.Path.Companion.toPath
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

internal object AndroidAppContextProvider : KoinComponent {
    val context: Context by inject()
}

actual fun createDataStore(): DataStore<Preferences> {
    val appContext = AndroidAppContextProvider.context.applicationContext
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            File(appContext.filesDir, DATA_STORE_FILE_NAME).absolutePath.toPath()
        }
    )
}
