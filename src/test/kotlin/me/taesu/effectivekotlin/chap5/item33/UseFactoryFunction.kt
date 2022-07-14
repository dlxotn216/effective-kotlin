package me.taesu.effectivekotlin.chap5.item33

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/14.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseFactoryFunction {
    @Test
    fun `생성자 대신 팩토리 함수를 사용하라`() { // 단, 기본 생성자 대신 사용하라는 것이 아니다
        val linkedList = myLinkedListOf(1, 2, 3)

        val intSize3List = ArrayList<Int>(3)    // 3이 뭘 의미하는지 유추 힘듦
        val intSize3ListWithFactory = ArrayLists.withSize<Int>(3)
    }

}

class MyLinkedList<T>(
    val head: T,
    val tail: MyLinkedList<T>?
)

// 생성자와 다르게 이름을 붙일 수 있다
fun <T> myLinkedListOf(
    vararg elements: T
): MyLinkedList<T>? {
    if (elements.isEmpty()) {
        return null
    }

    val head = elements.first()
    val elementsTail = elements.copyOfRange(1, elements.size)
    val tail = myLinkedListOf(*elementsTail)
    return MyLinkedList(head, tail)
}

object ArrayLists {
    fun <T> withSize(size: Int): ArrayList<T> = ArrayList(size)
}