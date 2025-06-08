package br.com.conding.tv.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import br.com.conding.tv.features.account.data.datasource.local.LocalStorage
import br.com.conding.tv.features.account.data.datasource.local.LocalStorageImpl
import br.com.conding.tv.storage.createDataStore
import org.koin.dsl.module

val commonModule = module {
    single<LocalStorage> {
        LocalStorageImpl(dataStore = get())
    }
    single<DataStore<Preferences>> {
        createDataStore()
    }
}