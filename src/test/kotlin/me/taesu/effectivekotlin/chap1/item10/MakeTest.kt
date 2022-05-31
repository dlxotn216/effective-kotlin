package me.taesu.effectivekotlin.chap1.item10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/05/31.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class MakeTest {
    // 아래를 대상으로 테스트를 작성하라
    // 복잡한 부분
    // 계속해서 수정이 발생하고 리팩터링이 일어날 수 있는 부분
    // 비즈니스 로직 부분
    // 공용 API 부분
    // 문제가 자주 발생하는 부분
    // 수정해야 하는 프로덕션 부분

    @Test
    fun `피보나치 수열 생성 테스트`() {
        // when
        var numbers = getFiboNumbers(1)

        // then
        assertThat(numbers[0]).isEqualTo(1)

        // when
        numbers = getFiboNumbers(5)

        // then
        assertThat(numbers[0]).isEqualTo(1)
        assertThat(numbers[1]).isEqualTo(1)
        assertThat(numbers[2]).isEqualTo(2)
        assertThat(numbers[3]).isEqualTo(3)
        assertThat(numbers[4]).isEqualTo(5)

        // when
        numbers = getFiboNumbers(-1) // 잘못된 파라미터에 대한 처리를 하는 지 테스트

        // then
        assertThat(numbers).isEmpty()
    }

    private fun getFiboNumbers(take: Int): List<Int> {
        if (take <= 0) return emptyList()

        val se = sequence {
            var pair = 1 to 1
            yield(pair.first)

            while (true) {
                yield(pair.second)
                pair = pair.second to pair.first + pair.second
            }
        }
        return se.take(take).toList()
    }

    @Test
    fun `소수 생성 테스트`() {
        var primes = getPrimeNumberSequence(15)
        assertThat(primes).isEqualTo(listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47))

        primes = getPrimeNumberSequence(-1)
        assertThat(primes).isEmpty()

        primes = getPrimeNumberSequence(null)
        assertThat(primes).isEmpty()

        // primes = getPrimeNumberSequence(Int.MAX_VALUE)
        // assertThat(primes).isNotEmpty
    }

    private fun getPrimeNumberSequence(take: Int?): List<Int> {
        take ?: return emptyList()
        if (take <= 0) return emptyList()

        var sequence = generateSequence(2) { it + 1 }
        val numbers = sequence {
            while (true) {
                val number = sequence.first()
                yield(number)
                sequence = sequence.filter { it % number != 0 }
            }
        }
        return numbers.take(take).toList()
    }

}