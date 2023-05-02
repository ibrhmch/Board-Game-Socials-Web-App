package test.goodboards.app.analyzer

import com.goodboards.app.analyzer.DatabaseInit
import com.goodboards.db.DriverManagerWrapper
import com.goodboards.db.SystemWrapper
import com.goodboards.redis.RedisInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import org.junit.Test
import kotlinx.serialization.json.Json
import java.sql.Connection
import kotlin.test.assertEquals


@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class NewsUnit(val gameID: String, var title: String, var description: String, val url: String)

class AnalyzerWorkerTest{

}