package me.taesu.effectivekotlin.chap2.item13

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/02.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class DONTReturnNullableUnit {
    @Test
    fun `Unit?을 리턴하지 마라`() {
        throwIfInvalid("correct")
    }

    fun throwIfInvalid(key: String) {
        if(!isKeyCorrect(key)) return   // 이 코드가 더 명확하다
        verifyKey(key) ?: return    // Unit? 리턴인 경우 return
    }

    fun isKeyCorrect(key: String): Boolean {
        return key == "correct"
    }

    fun getDate():String? {
        return ""
    }

    // 이런식으로 짜면 호출부에서 읽기 어려워진다.
    fun verifyKey(key: String): Unit? {
        return if (key == "correct") {
            Unit
        } else {
            null
        }
    }
}