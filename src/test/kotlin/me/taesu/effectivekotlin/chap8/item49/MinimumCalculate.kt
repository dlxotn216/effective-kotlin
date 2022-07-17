package me.taesu.effectivekotlin.chap8.item49

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class MinimumCalculate {
    @Test
    fun `최소연산`() {
        // find, take, any, all, none, indexOf는 최소연산 적용 가능

        // filter 1
        // map 1
        // filter 2
        // filter 3
        // map 3
        (1..10).asSequence()
            .filter {
                println("filter $it")
                it % 2 == 1
            }
            .map {
                println("map $it")
                it * 2
            }
            .find { it > 5 }


        // filter 1
        // filter 2
        // filter 3
        // filter 4
        // filter 5
        // filter 6
        // filter 7
        // filter 8
        // filter 9
        // filter 10
        // map 1
        // map 3
        // map 5
        // map 7
        // map 9
        println()
        (1..10)
            .filter {
                println("filter $it")
                it % 2 == 1
            }
            .map {
                println("map $it")
                it * 2
            }
            .find { it > 5 }
    }


}