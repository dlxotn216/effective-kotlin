package me.taesu.effectivekotlin.chap5.item34

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/14.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseNamedArgumentToDefaultConstructor {
    @Test
    fun `기본 생성자에 이름있는 옵션 아규먼트를 사용하라`() {
        val pizza1 = Pizza("L", 0, 3) // 무슨 피자인지 알 수 있나?

        // 라지사이즈의 올리브 토핑 세번인 피자
        val pizza2 = Pizza(size = "L", cheese = 0, olives = 3)

        assertThat(pizza1).isEqualTo(pizza2)
    }

}

data class Pizza(
    val size: String,
    val cheese: Int = 0,
    val olives: Int = 0,
    val bacon: Int = 0
) {
    // 점층적 생성자 패턴
    constructor(size: String, cheese: Int, olives: Int): this(size, cheese, olives, 0)
    constructor(size: String, cheese: Int): this(size, cheese, 0)
    constructor(size: String): this(size, 0)

}