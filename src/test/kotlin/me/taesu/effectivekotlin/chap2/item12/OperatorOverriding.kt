package me.taesu.effectivekotlin.chap2.item12

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/02.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class OperatorOverriding {
    @Test
    fun `연산자 오버로딩은 의미에 맞게`() {
        assertThat(6.factorial()).isEqualTo(6 * 5 * 4 * 3 * 2 * 1)
        assertThat(!6).isEqualTo(6 * 5 * 4 * 3 * 2 * 1)
        // !6을 팩토리얼로 쓰는 순간 모호해짐
    }

    @Test
    fun `오버로딩은 분명하게 사용`() {
        // 아래는 피연산자에 따라 동작이 다름
        3 * { println("테스트")}   // 세번 호출한다
        3 * 5   // 3과 5를 곱한다

        3 repeatTimes  { println("테스트")}    // 차라리 infix를 쓰자
        repeat(3) { println("테스트")} // 근데 이미 stdlib에 있어서 위 경우엔 굳이..
    }

}

fun Int.factorial() = (1..this).reduce { acc, i -> acc * i }
operator fun Int.not() = this.factorial()
operator fun Int.times(operation: () -> Unit) = repeat(this) {
    operation()
}

infix fun Int.repeatTimes(operation: () -> Unit) = repeat(this) {
    operation()
}