package me.taesu.effectivekotlin.chap8.item49

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class WhenSequenceNotFast {
    @Test
    fun `시퀀스가 빠르지 않은 경우`() {
        // 정렬과 같이 전체를 기반으로 처리해야 하는 연산은 시퀀스가 비효율적
        listOf(5, 2, 1, 3).sorted()

        // 정렬은 예외적으로 컬렉션이 더 좋다
        sequenceOf(5, 2, 1, 3).sorted()
    }

}