package me.taesu.effectivekotlin.chap6.item44

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class AvoidMemberExtendFunction {
    @Test
    fun `멤버 확장 함수는 사용하지 말 것`() {
        val ph = PhoneBook("99952723")
        ph.apply {
            // 여기서 호출이 가능 함..
            // 가시성 제한 하고 싶으면 private을 붙일 것
            number.isPhoneNumber()
        }
    }

}

class PhoneBook(val number: String) {
    fun String.isPhoneNumber() = length == 8 && all { it.isDigit() }
}

class A {
    val a = 10
}

class B {
    val a = 20
    val b = 30
    fun A.test() = a + b    // 40 ? 50 ?
}