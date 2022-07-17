package me.taesu.effectivekotlin.chap7.item47

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseInlineClass {
    @Test
    fun `인라인 클래스 사용을 고려하라`() {
        val name = Name("Lee")
        // val name = "Lee"
        val name2 = JvmName("Lee")

        // 하나의 값을 보유한 객체도 인라인 됨

    }
}

inline class Name(private val value: String)

@JvmInline
value class JvmName(private val value: String)

@JvmInline
value class Key(private val key: Long)

// Entity에 이렇게 쓰면 좋을 듯
class User(
    val userKey: Key
)