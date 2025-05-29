package br.com.conding.tv

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import br.com.conding.tv.features.account.data.repository.LocalStorageImp
import br.com.conding.tv.networking.storage.LocalStorage
import br.com.conding.tv.networking.storage.createDataStore
import org.koin.dsl.module

val androidModule = module {
    single<DataStore<Preferences>> {
        createDataStore()
    }
    single<LocalStorageImp> {
        LocalStorage(dataStore = get())
    }
    single {
        LocalStorage(dataStore = get())
    }
}
