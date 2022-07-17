package me.taesu.effectivekotlin.chap8.item49

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class InfiniteSequence {
    @Test
    fun `무한 시퀀스`() {
        val primeSequence = sequence {
            var sequence = generateSequence(2) { it + 1 }
            while (true) {
                sequence.first().apply {
                    yield(this)
                    sequence = sequence.filter { it % this != 0 }
                }
            }
        }

        println(primeSequence.take(15).toList())
    }

}