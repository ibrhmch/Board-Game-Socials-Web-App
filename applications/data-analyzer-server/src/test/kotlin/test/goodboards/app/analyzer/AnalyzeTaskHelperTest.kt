package test.goodboards.app.analyzer

import com.goodboards.app.analyzer.AnalyzerTask
import com.goodboards.app.analyzer.AnalyzerWorkFinder
import com.goodboards.app.analyzer.AnalyzerWorker
import com.goodboards.app.analyzer.tasks.AnalyzerNewsTaskHelper
import com.goodboards.app.analyzer.tasks.Wrapper
import com.goodboards.workflow.WorkScheduler
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkObject
import org.junit.Test

class AnalyzeTaskHelperTest {
    @Test
    fun testAnalyzeTaskHelper(){
        mockkObject(Wrapper)
        val finder = mockk<AnalyzerWorkFinder>()
        val worker = mockk<AnalyzerWorker>()
        every { Wrapper.getAnalyzerWorkFinder() } returns finder
        every { Wrapper.getAnalyzerWorker() } returns worker

        mockkConstructor(WorkScheduler:: class)
        every { anyConstructed<WorkScheduler<AnalyzerTask>>().start() } returns Unit

        AnalyzerNewsTaskHelper.scheduleAnalyzerNewsTask()
    }
}