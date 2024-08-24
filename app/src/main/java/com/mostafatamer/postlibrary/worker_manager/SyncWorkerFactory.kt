package com.mostafatamer.postlibrary.worker_manager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.mostafatamer.postlibrary.domain.use_case.MockServerUseCase
import javax.inject.Inject

class SyncWorkerFactory @Inject constructor(
    private val mockServerUseCase: MockServerUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker {
        return SyncWorker(appContext, workerParameters, mockServerUseCase)
    }
}