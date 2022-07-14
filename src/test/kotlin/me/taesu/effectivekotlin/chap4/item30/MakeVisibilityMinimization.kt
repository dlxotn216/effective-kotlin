package me.taesu.effectivekotlin.chap4.item30

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/09.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class MakeVisibilityMinimization {
    @Test
    fun `요소의 가시성을 최소화 하라`() {
        // given
        val intSet = CounterSet<Int>()

        // when
        intSet.add(2)
        intSet.add(3)
        intSet.addAll(listOf(1, 2, 3, 4, 5))

        // then
        assertThat(intSet.elementsAdded).isEqualTo(7)
    }
}

class CounterSet<T>(
    private val innerSet: MutableSet<T> = mutableSetOf()
): MutableSet<T> by innerSet {
    var elementsAdded: Int = 0
        private set // 외부로부터 set을 숨겨 불변성을 지킴

    override fun add(element: T): Boolean {
        elementsAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elementsAdded += elements.size
        return innerSet.addAll(elements)
    }
}

// 클래스 멤버
// public 어디서나
// private 클래스 내부에서
// protected 클래스와 서브클래스 내부
// internal 모듈 내부에서

// 톱레벨 요소
// public 어디서나
// private 같은 파일 내부에서
// internal 모듈 내부에서

// 모듈이란 그레이들 소스 세트, 메이븐 프로젝트, 인텔리제이 모듈 등