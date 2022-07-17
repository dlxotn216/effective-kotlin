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
class UseSequenceWhenOneMoreProcess {
    @Test
    fun `하나 이상의 처리 단계를 가진 경우는 시퀀스를`() {
        // filter 1
        // map 1
        // filter 2
        // filter 3
        // map 3
        val list1 = sequenceOf(1, 2, 3)
            .filter {
                println("filter $it")
                it % 2 == 1
            }
            .map {
                println("map $it")
                it * 2
            }
            .toList()
        // 시퀀스는 요소 하나당 지정 연산을 한꺼번에 적용
        // element by element order or lazy order

        // 아래랑 똑같은 순서
        println()
        listOf(1, 2, 3).forEach {
            println("filter $it")
            if (it % 2 == 1) {
                println("map $it")
            }
        }


        // filter 1
        // filter 2
        // filter 3
        // map 1
        // map 3
        println()
        val list2 = listOf(1, 2, 3)
            .filter {
                println("filter $it")
                it % 2 == 1
            }
            .map {
                println("map $it")
                it * 2
            }

        assertThat(list1).isEqualTo(list2)
    }
}