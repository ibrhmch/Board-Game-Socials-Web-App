package com.goodboards.app.analyzer.tasks

import com.goodboards.app.analyzer.AnalyzerTask
import com.goodboards.app.analyzer.AnalyzerWorkFinder
import com.goodboards.app.analyzer.AnalyzerWorker
import com.goodboards.workflow.WorkScheduler

object AnalyzerNewsTaskHelper {

    fun scheduleAnalyzerNewsTask(){
        val finder = Wrapper.getAnalyzerWorkFinder()
        val worker = Wrapper.getAnalyzerWorker()
        val scheduler = WorkScheduler<AnalyzerTask>(finder, mutableListOf(worker), 30)
        scheduler.start()
    }
}