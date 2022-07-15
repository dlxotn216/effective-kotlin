package me.taesu.effectivekotlin.chap6.item42

import me.taesu.effectivekotlin.chap6.item42.User.Companion.DISPLAY_ORDER
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/15.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class CompareToContract {
    @Test
    fun `compareTo 규약을 지켜라`() {
        val abc = Word("abc")
        val def = Word("def")
        val ghi = Word("ghi")

        val abcAnother = Word("abc")

        // 비대칭적 동작
        // a>=b && a<=b 이면 a = b
        assertThat(
            abc >= abcAnother && abc <= abcAnother
                && abc == abcAnother       // equals도 오버라이딩 하라는 소리
        ).isTrue

        // 코넥스적 동작, 두 요소는 둘 중 하나의 확실한 관계를 가져야 한다
        // a>=b or b<=a 둘중 하나는 true이어야 함
        assertThat(abc <= def || abc <= def).isTrue

        // 대칭적 동작
        assertThat(abc <= def).isTrue
        assertThat(def <= ghi).isTrue
        assertThat(abc <= ghi).isTrue
    }

    @Test
    fun `sortedBy, sortedWith`() {
        val words = listOf(Word("erabc"), Word("zabc"), Word("abc"))
        val sortedBy = words.sortedBy { it.value }
        println(sortedBy)

        val users = listOf(User("lee", "taesu"), User("lee", "ataesu"))
        val sortedWith = users.sortedWith(
            compareBy({ it.name }, { it.surname }),
        )
        val another = users.sortedWith(DISPLAY_ORDER)
        println(sortedWith)
        assertThat(another).isEqualTo(sortedWith)
    }

}

class Word(
    val value: String
): Comparable<Word> {
    override fun compareTo(other: Word): Int {
        return value.compareTo(other.value)
    }

    override fun equals(other: Any?): Boolean {
        return other is Word
            && other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value
    }
}

class User(
    val name: String,
    val surname: String
) {
    override fun toString(): String {
        return "$name, $surname"
    }

    // 여러 구현 방법이 있다
    fun compareTo(other: User): Int = compareValues(name, other.name)
    fun compareTo2(other: User): Int = compareValuesBy(
        this,
        other,
        { it.name },
        { it.surname },
    )

    // 자주 사용하는 것은 companion object 안에 둘수도
    companion object {
        val DISPLAY_ORDER = compareBy<User>({ it.name }, { it.surname })
    }
}