package com.mostafatamer.postlibrary.worker_manager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mostafatamer.postlibrary.domain.model.SYNC_WORKER_ERROR
import com.mostafatamer.postlibrary.domain.state.DataState
import com.mostafatamer.postlibrary.domain.use_case.MockServerUseCase
import com.mostafatamer.postlibrary.pushNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    @Assisted private val mockServerUseCase: MockServerUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            context.pushNotification("Syncing Data", "Syncing favorite posts...")
            val result = mockServerUseCase.syncFavoritePosts()

            if (result is DataState.Error) throw result.exception

            context.pushNotification("Syncing Data", "Syncing completed!")

            Result.success()
        } catch (e: Exception) {
            context.pushNotification("Error", "Error Syncing favorite posts: ${e.message}")
            Log.d(SYNC_WORKER_ERROR, "Error Syncing favorite posts: ${e.message}")
            Result.retry()
        }
    }
}
