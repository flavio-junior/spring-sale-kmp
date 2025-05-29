package br.com.conding.tv.networking.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createDataStore(): DataStore<Preferences>