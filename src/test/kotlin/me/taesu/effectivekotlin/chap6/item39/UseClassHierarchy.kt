package me.taesu.effectivekotlin.chap6.item39

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/15.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseClassHierarchy {
    @Test
    fun `태그 클래스보다 클래스 계층을 사용하라`() {
        ValueMatcher.equal(23).match(23)

        val unused = listOf(1, 2, 3)
        ValueMatcher.emptyList(unused).match(emptyList())   // 실제 사용되지도 않음

        assertThat(SealedValueMatcher.Equal(1).match(1)).isEqualTo(true)
        assertThat(SealedValueMatcher.Empty<List<Int>>().match(emptyList())).isEqualTo(true)
    }
}

class ValueMatcher<T> private constructor(
    private val value: T? = null,
    private val matcher: Matcher
) {
    // value 프로퍼티가 특정 matcher에서만 사용 됨.
    // 클래스가 확장 될 수록 더욱 공통이 늘어날 수 있음.
    fun match(value: T?) = when (matcher) {
        Matcher.EQUAL -> value == this.value
        Matcher.NOT_EQUAL -> value != this.value
        Matcher.LIST_EMPTY -> value is List<*> && value.isEmpty()
        Matcher.LIST_NOT_EMPTY -> value is List<*> && value.isNotEmpty()
    }

    enum class Matcher {
        EQUAL,
        NOT_EQUAL,
        LIST_EMPTY,
        LIST_NOT_EMPTY
    }

    companion object {
        fun <T> equal(value: T) = ValueMatcher(value, Matcher.EQUAL)
        fun <T> notEqual(value: T) = ValueMatcher(value, Matcher.NOT_EQUAL)
        fun <T> emptyList(value: T) = ValueMatcher(value, Matcher.LIST_EMPTY)
        fun <T> notEmptyList(value: T) = ValueMatcher(value, Matcher.LIST_NOT_EMPTY)
    }
}

// Sealed로 계층을 구성
sealed class SealedValueMatcher<T> {
    abstract fun match(value: T): Boolean

    class Equal<T>(private val value: T): SealedValueMatcher<T>() {
        override fun match(value: T): Boolean {
            return this.value == value
        }
    }

    class NotEqual<T>(private val value: T): SealedValueMatcher<T>() {
        override fun match(value: T): Boolean {
            return this.value != value
        }
    }

    class Empty<T>: SealedValueMatcher<T>() {
        override fun match(value: T): Boolean {
            return value is List<*> && value.isEmpty()
        }
    }

    class NotEmpty<T>: SealedValueMatcher<T>() {
        override fun match(value: T): Boolean {
            return value is List<*> && value.isNotEmpty()
        }
    }
}