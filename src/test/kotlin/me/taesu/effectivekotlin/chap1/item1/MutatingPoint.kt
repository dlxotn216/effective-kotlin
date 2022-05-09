package me.taesu.effectivekotlin.chap1.item1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

/**
 * Created by itaesu on 2022/05/07.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class MutatingPoint {
    // Delegate를 통해 감지도 할 수 있음
    var names by Delegates.observable(listOf<String>()) { _, old, new ->
        println("$old to $new changed")
    }

    @Test
    fun `불변 컬렉션보단 불변 프로퍼티를`() {
        names += "taesu"
        names += "lee"

        User("taesu").apply {
            updateFriends(listOf("lee", "kim"))
            addFriends("young")
            println(friends)
            assertThat(friends).isEqualTo(listOf("lee", "kim", "young"))
        }

        User2("taesu").apply {
            addFriends("young")
            println(friends)
            assertThat(friends).isEqualTo(listOf("young"))
        }
    }


    // private set으로 내부 숨기기
    class User(val id: String) {
        var friends: List<String> = listOf()
            private set

        fun addFriends(friend: String) {
            friends += friend
        }

        fun updateFriends(friends: List<String>) {
            this.friends = friends
        }
    }

    // 뒷받침 필드를 통해서 숨기기
    class User2(val id: String) {
        private val _friends: MutableList<String> = mutableListOf()
        val friends: List<String>
            get() = _friends

        fun addFriends(friend: String) {
            _friends += friend
        }
    }
}