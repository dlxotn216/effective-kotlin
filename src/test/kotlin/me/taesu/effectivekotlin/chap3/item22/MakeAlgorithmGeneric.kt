package me.taesu.effectivekotlin.chap3.item22

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/08.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class MakeAlgorithmGeneric {
    @Test
    fun `일반적 알고리즘을 만들 때 제네릭을 이용하라`() {
        fun <T> Iterable<T>.filterItems(
            predicate: (T) -> (Boolean)
        ): MutableList<T> {
            val result = mutableListOf<T>()
            for (e in this) {
                if (predicate(e)) {
                    result += e
                }
            }
            return result
        }
    }

    @Test
    fun `제네릭 제한`() {
        fun <T: Comparable<T>> Iterable<T>.sortedItems(
        ) {
        }

        // R: Any는 null이 아닌 타입 제한을 의미 함
        fun <T, R: Any> Iterable<T>.mapNotNullItems(
            transform: (T) -> (R)
        ): List<R> {
            return this.map { transform(it) }
        }

        // 여러 제한을 두기도 가능
        fun <T: Animal> pet1(animal: T) where T: GoodTempered {}
        fun <T> pet2(animal: T) where T: Animal, T: GoodTempered {}
    }

    interface Animal
    interface GoodTempered {
        fun tempered()
    }


}