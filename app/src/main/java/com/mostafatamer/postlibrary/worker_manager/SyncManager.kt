package com.mostafatamer.postlibrary.worker_manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class SyncManager(private val context: Context) {
    fun scheduleSync(
        repeatingInterval: Long,
        timeUnit: TimeUnit,
    ) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicSyncRequest =
            PeriodicWorkRequestBuilder<SyncWorker>(repeatingInterval, timeUnit)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "SyncFavoritesWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncRequest
        )
    }
}
