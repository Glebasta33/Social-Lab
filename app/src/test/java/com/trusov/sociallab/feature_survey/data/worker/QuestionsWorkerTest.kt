package com.trusov.sociallab.feature_survey.data.worker

import android.content.Context
import androidx.work.testing.TestListenableWorkerBuilder
import com.trusov.sociallab.App
import org.junit.Assert.*
import androidx.work.ListenableWorker.*
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test

class QuestionsWorkerTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = App().getAppContext()
    }

    @Test
    fun testQuestionWorkerResult() {
        val worker = TestListenableWorkerBuilder<QuestionsWorker>(
            context = context
        ).build()
        runBlocking {
            val result = worker.startWork().get()
            assertEquals(result, Result.success())
        }
    }

    @After
    fun tearDown() {
    }
}