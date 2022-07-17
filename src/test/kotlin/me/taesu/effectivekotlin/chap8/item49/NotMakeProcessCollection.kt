package me.taesu.effectivekotlin.chap8.item49

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class NotMakeProcessCollection {
    @Test
    fun `중간 컬렉션 만들지 않음`() {
        listOf(1, 2, 3, 4)   // collection 0
            .filter { it % 10 == 0 } // collection 1
            .map { it * 2 } // collection 2
            .sum()
        // 총 세개의 컬렉션을 사용 함

        sequenceOf(1, 2, 3, 4)
            .filter { it % 10 == 0 }
            .map { it * 2 }
            .sum()
        // 컬렉션을 만들지 않음
    }

}