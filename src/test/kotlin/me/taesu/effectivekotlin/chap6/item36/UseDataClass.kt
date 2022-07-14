package me.taesu.effectivekotlin.chap6.item36

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/14.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseDataClass {
    @Test
    fun `데이터 집합엔 data 한정자 사용`() {
        val player = Player(1L, "lee", 10)
        val samePlayer = Player(1L, "lee", 10)

        assertThat(player).isEqualTo(samePlayer)

        val copied = player.copy(key = 23, name = "test")
        assertThat(copied.key).isEqualTo(23L)
        assertThat(copied.name).isEqualTo("test")
        assertThat(copied).isNotEqualTo(player)

        val (key, name) = player
        assertThat(key).isEqualTo(1L)
        assertThat(name).isEqualTo("lee")

        // 이름을 따라가는게 아니므로 절대 주의
        val (_key, points) = player
        // points는 사실 name 자리임
        assertThat(points).isEqualTo("lee")
    }

}

data class Player(
    val key: Long,
    val name: String,
    val points: Int
)