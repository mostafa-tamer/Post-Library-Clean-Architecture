package com.mostafatamer.postlibrary

import android.app.Application
import androidx.work.Configuration
import com.mostafatamer.postlibrary.worker_manager.SyncWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var syncWorkerFactory: SyncWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(syncWorkerFactory)
            .build()
}

