package me.taesu.effectivekotlin.chap6.item41

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/15.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class HashcodeContract {
    @Test
    fun `hashCode의 규약을 지켜라`() {
        val name1 = FullName("lee", "taesu")
        val set = mutableSetOf<FullName>()
        set.add(name1)

        name1.surname = "changed"

        // set, map의 키로 mutable 한 객체를 절대 사용하지 말것
        assertThat(name1 in set).isFalse
        assertThat(set.first() == name1).isTrue
    }

    @Test
    fun `equals도 반드시 오버라이딩 할 것`() {
        val name1 = BadFullName("lee", "taesu")
        val name2 = BadFullName("lee", "taesu")
        val badSet = mutableSetOf<BadFullName>()
        badSet.add(name1)

        // name2는 안들어가잇는데 first가 동일한 상황
        assertThat(name2 in badSet).isFalse
        assertThat(badSet.first() == name2).isTrue
    }
}

data class FullName(
    var name: String,
    var surname: String
)

class BadFullName(
    var name: String,
    var surname: String
) {
    override fun equals(other: Any?): Boolean {
        return other is BadFullName
            && other.name == name
            && other.surname == surname
    }
}