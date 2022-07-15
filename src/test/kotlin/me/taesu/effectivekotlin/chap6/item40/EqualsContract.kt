package me.taesu.effectivekotlin.chap6.item40

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

/**
 * Created by itaesu on 2022/07/15.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class EqualsContract {
    @Test
    fun `equals 규약을 지켜라`() {
        // 반사적 동작
        val user1 = User(12L, "taesu", "lee")
        assertThat(user1 == user1).isTrue

        // 대칭적 동작
        val user2 = User(12L, "taesu", "lee")
        assertThat(user1 == user2).isTrue
        assertThat(user2 == user1).isTrue

        // 연속적 동작
        val user3 = User(12L, "taesu", "lee")
        assertThat(user1 == user2).isTrue
        assertThat(user2 == user3).isTrue
        assertThat(user3 == user1).isTrue // also

        // (DateTime에서 other가 Date인지 비교해서 동등비교를 한다면 깨짐)
        val date1 = Date(2022, 7, 15)
        val dateTime1 = DateTime(date1, 12, 34, 56)
        val dateTime2 = DateTime(date1, 12, 22, 11)
        assertThat(dateTime1 == date1).isTrue   // a = b
        assertThat(date1 == dateTime2).isTrue   // b = c
        assertThat(dateTime2 == dateTime1).isFalse  // c != a??

        // 일관적 동작 (값이 바뀌지 않는 선에서 항상 동일한 동작을 보여야 한다)
        // java의 URL은 IP 주소를 가져와서 비교를 함
        // URL에 해당하는 IP가 항상 같을리 없으므로 일관적이지 않음
        // 게다가 equals, hashcode는 항상 빠른 동작을 해야 함 (collection 내부에서 많이 사용하므로)
        // 네트워크에 의존적이므로 느리고, 유동적인 값을 가지고 비교를 하므로 일관적이지 않음
        repeat(100) {
            assertThat(user1 == user2).isTrue
        }

        // null 관련 동작 (object가 null이 아니라면 언제나 null 비교시 false)
        assertThat(user1 == null).isFalse
    }

}

// equals만 정의했기에 warning이 뜬다.
class MyDateTime(
    private var millis: Long = 0L,
    private var timeZone: TimeZone? = null
) {
    private var asStringCache = ""
    private var changed = false

    // 실제 값(data)에 영향을 주는 프로퍼티만 equals를 검사한다.
    override fun equals(other: Any?): Boolean {
        return other is MyDateTime &&
            other.millis == millis &&
            other.timeZone == timeZone
    }
}

// 위와 동일함
data class MyDateTimeData(
    private var millis: Long = 0L,
    private var timeZone: TimeZone? = null
) {
    private var asStringCache = ""
    private var changed = false
}

class User(
    val key: Long,
    val name: String,
    val surName: String
) {
    // Entity의 경우 key가 같으면 동일하다고 취급하므로
    override fun equals(other: Any?): Boolean {
        return other is User &&
            other.key == key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}

open class Date(val year: Int, val month: Int, val day: Int) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is DateTime -> other.date == this
            is Date -> other.year == year &&
                other.month == month &&
                other.day == day
            else -> false
        }
    }
}

class DateTime(
    val date: Date,
    val hour: Int,
    val minute: Int,
    val second: Int
): Date(date.year, date.month, date.day) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is DateTime -> other.date == date && other.hour == hour
                && other.minute == minute && other.second == second
            is Date -> other == date
            else -> false
        }
    }
}