package com.goodboards.app.analyzer.tasks

import com.goodboards.app.analyzer.AnalyzerWorkFinder
import com.goodboards.app.analyzer.AnalyzerWorker

object Wrapper {
    fun getAnalyzerWorkFinder(): AnalyzerWorkFinder{
        return AnalyzerWorkFinder()
    }

    fun getAnalyzerWorker(): AnalyzerWorker{
        return AnalyzerWorker()
    }
}