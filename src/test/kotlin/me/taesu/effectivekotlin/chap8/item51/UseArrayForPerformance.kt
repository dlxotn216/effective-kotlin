package me.taesu.effectivekotlin.chap8.item51

import org.junit.jupiter.api.Test
import org.springframework.util.StopWatch

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseArrayForPerformance {
    @Test
    fun `성능이 중요한 곳엔 기본 자료형 배열을`() {
        StopWatch().apply {
            start()
            IntArray(1_000_000) { it }.average()
            stop()
            println(shortSummary())
        }

        StopWatch().apply {
            start()
            List(1_000_000) { it }.average()
            stop()
            println(shortSummary())
        }
    }

}