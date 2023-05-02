package test.goodboards.app.analyzer

import com.goodboards.app.analyzer.AnalyzerWorkFinder
import org.junit.Test

class AnalyzerFinderTest {
    @Test
    fun testAnalyzerFinder(){
        val finder = AnalyzerWorkFinder()
        val items = finder.findRequested("some info")

        assert(items is MutableList)
        assert(items.size == 1)
        assert(items[0].info == "some info")
    }
}