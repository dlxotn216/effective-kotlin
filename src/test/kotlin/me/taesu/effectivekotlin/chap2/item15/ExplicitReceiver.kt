package me.taesu.effectivekotlin.chap2.item15

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/02.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class ExplicitReceiver {
    val thisProp = 3
    fun somMethod() {
        println("hello")
    }

    @Test
    fun `명시적으로 리시버를 참조하라`() {
        thisProp
        this.thisProp   // 클래스도 this를 명시적으로
        this.somMethod()   // 클래스도 this를 명시적으로
        assertThat(listOf(5, 1, 4, 2, 3).quickSort()).isEqualTo(listOf(1, 2, 3, 4, 5))
    }

    @Test
    fun `특히나 여러 리시버가 있으면 명시적으로 리시버를 참조하라`() {
        Node("Parent").makeChild("Child")

    }
}

// 확장메서드에서도 this를 명시적으로 참조할 것
fun <T: Comparable<T>> List<T>.quickSort(): List<T> {
    // if(size < 2) return this
    if (this.size < 2) return this
    val pivot = first()
    val (left, right) = drop(1).partition { it < pivot }
    return left.quickSort() + pivot + right.quickSort()
}

class Node(val name: String) {
    fun makeChild(child: String) {
        create("$name.$child").apply {
            // create가 Node?라서 문제임
            println("Created $name")             // Created Parent
            // println("Created ${this.name}") // 명시적으로 리시버 붙이면 컴파일 에러
            println("Created ${this?.name}")     // Created Parent.Child
        }

        // also나 let을 사용해서 this가 겹치지 않게 해주는게 좋다
        // 즉 리시버를 명시적으로 붙이고 마찬가지로 리시버를 명시적으로 분리하면 좋다. (this가 안겹치게)
        // -> 짧게 적을 수 있다고 해서 리시버 생략하지 말 것.
        create("$name.$child").also {
            println("Created from ${this.name}")    // Created from Parent
            println("Created ${it?.name}")          // Created Parent.Child
        }
    }

    private fun create(name: String): Node? = Node(name)
}