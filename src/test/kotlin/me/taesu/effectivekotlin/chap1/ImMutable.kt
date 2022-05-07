package me.taesu.effectivekotlin.chap1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

/**
 * Created by itaesu on 2022/05/07.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class ImMutable {
    @Test
    fun `Mutable 컬렉션보단 읽기전용 컬렉션을 사용하라`() {
        val list = listOf(1, 2, 3)
        if (list is MutableList) {  // 이런식으로 사용하지 말 것, 타입은 맞다 그래서 캐스팅은 된다.
            try {
                list.add(4)     // UnsupportedOperationException
            } catch (e: UnsupportedOperationException) {
                e.printStackTrace()
            }
        }

        val mutableList = list.toMutableList().apply {
            add(4)
        }   // toMutableList를 통해 새로운 컬렉션을 복제해서 사용할 것

        assertThat(mutableList.contains(4)).isTrue
    }


    @Test
    fun `불변 객체가 필요하다면 Data 클래스를`() {
        /*
            데이터 넣는 순서와 변경할 노드 순서가 중요
                taesu2                      taesu2
            taesu1   taesu3         ->  taesu4   taesu3
            이러면 taesu4로 바뀐 노드는 못찾음
            root 기준 오른쪽에 있어야 하는데 왼쪽에 있어버리니
         */
        fun testWithTree() {
            val user1 = MutableUser("taesu1", "taesu")
            val set1 = setOf(user1)
            assertThat(set1.contains(user1)).isTrue

            val set2: SortedSet<MutableUser> = TreeSet()
            set2.add(user1)
            set2.add(MutableUser("taesu2", "이태수2"))
            set2.add(MutableUser("taesu3", "이태수3"))
            print(set2)
            assertThat(set2.contains(user1)).isTrue


            user1.id = "taesu4"
            print(set2)
            assertThat(user1 in set1).isTrue
            assertThat(user1 in set2).isFalse
        }
        testWithTree()

        fun testWithHashMap() {
            val user1 = MutableUser("A taesu", "taesu")
            val set2: MutableMap<MutableUser, MutableUser> = mutableMapOf()
            set2[user1] = user1
            MutableUser("taesu2", "이태수2").apply {
                set2[this] = this
            }
            MutableUser("taesu3", "이태수3").apply {
                set2[this] = this
            }
            print(set2)
            assertThat(set2.contains(user1)).isTrue


            user1.id = "Z taesu"
            print(set2)
            assertThat(user1 in set2).isFalse
        }
        testWithHashMap()

        val user1 = User("taesu1", "taesu")
        val set1 = setOf(user1)
        assertThat(set1.contains(user1)).isTrue

        val set2: SortedSet<User> = TreeSet()
        set2.add(user1)
        set2.add(User("taesu2", "이태수2"))
        set2.add(User("taesu3", "이태수3"))
        print(set2)
        assertThat(set2.contains(user1)).isTrue


        val changed = user1.copy(id = "taesu4")
        print(set2)
        assertThat(user1 in set1).isTrue
        assertThat(user1 in set2).isTrue
        assertThat(changed.id).isEqualTo("taesu4")
    }

    class MutableUser(
        var id: String,
        var name: String
    ): Comparable<MutableUser> {
        override fun compareTo(other: MutableUser): Int {
            return id.compareTo(other.id)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as MutableUser

            return id == other.id && name == other.name
        }

        override fun hashCode(): Int {
            var hash = id.hashCode()
            hash += 31 * name.hashCode()
            return hash
        }

        /**
         * Returns a string representation of the object.
         */
        override fun toString(): String {
            return """$id $name"""
        }
    }

    data class User(
        val id: String,
        val name: String
    ): Comparable<User> {
        override fun compareTo(other: User): Int {
            return id.compareTo(other.id)
        }
    }


}