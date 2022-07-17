package me.taesu.effectivekotlin.chap8.item52

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseMutableCollection {
    @Test
    fun `성능을 위해서 Mutable 컬렉션 사용을 고려하라`() {
        val list1 = listOf(1, 2, 3) + listOf(2, 3)
        /*
        public operator fun <T> Collection<T>.plus(elements: Iterable<T>): List<T> {
            if (elements is Collection) {
                val result = ArrayList<T>(this.size + elements.size)
                result.addAll(this)
                result.addAll(elements)
                return result
            } else {
                val result = ArrayList<T>(this)
                result.addAll(elements)
                return result
            }
        }
         */

        val list2 = mutableListOf(1, 2, 3)
        list2.addAll(mutableListOf(2, 3))


    }

}