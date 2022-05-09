package me.taesu.effectivekotlin.chap1.item2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/05/07.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class Capturing {
    @Test
    fun `소수 구하기 기본 예제`() {
        var numbers = (2..50).toList()
        val primes = mutableListOf<Int>()
        while (numbers.isNotEmpty()) {
            val prime = numbers.first()
            primes += prime
            numbers = numbers.filter { it % prime != 0 }
        }
        println(primes)
        assertThat(primes).isEqualTo(listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47))
    }

    @Test
    fun `소수 구하기 기본 예제 시퀀스 버전`() {
        val sequence = sequence {
            var numbers = generateSequence(2) { it + 1 }
            while (true) {
                val prime = numbers.first()
                yield(prime)
                numbers = numbers.filter { it % prime != 0 }
            }
        }

        val primes = sequence.take(15).toList()
        println(primes)
        assertThat(primes).isEqualTo(listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47))
    }

    @Test
    fun `소수 구하기 기본 예제 시퀀스 버전 캡처링 된 변수 버그`() {
        val sequence = sequence {
            var numbers = generateSequence(2) { it + 1 }
            var prime: Int
            while (true) {
                prime = numbers.first()
                yield(prime)
                numbers = numbers.filter { it % prime != 0 }
            }
        }

        val primes = sequence.take(15).toList()
        println(primes)
        assertThat(primes).isNotEqualTo((listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47)))
    }

    @Test
    fun `소수 구하기 기본 예제 시퀀스 변수 선언 안하는 버전`() {
        val sequence = sequence {
            var numbers = generateSequence(2) { it + 1 }
            while (true) {
                numbers.first().apply {
                    yield(this)
                    numbers = numbers.filter { it % this != 0 }
                }
            }
        }

        val primes = sequence.take(15).toList()
        println(primes)
        assertThat(primes).isEqualTo(listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47))
    }

    @Test
    fun `yield test`() {
        val sequence = sequence {
            yield(1)
            yieldAll(listOf(3, 4, 5))
            yieldAll(generateSequence(7) { it + 2 })
        }

        val numbers = sequence.take(10).toList()
        println(numbers)
        assertThat(numbers).isEqualTo(listOf(1, 3, 4, 5, 7, 9, 11, 13, 15, 17))
    }

    @Test
    fun `yield 피보나치`(){
        // 1 1 2 3 5
        val sequence = sequence {
            var pair = 0 to 1
            yield(pair.first + pair.second)
            while (true) {
                (pair.first + pair.second).apply {
                    yield(this)
                    pair = pair.second to this
                }
            }
        }

        val numbers = sequence.take(10).toList()
        println(numbers)
        assertThat(numbers).isEqualTo(listOf(1, 1, 2, 3, 5, 8, 13, 21, 34, 55))
    }
}