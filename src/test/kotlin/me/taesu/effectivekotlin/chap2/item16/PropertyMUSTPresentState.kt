package me.taesu.effectivekotlin.chap2.item16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/02.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class PropertyMUSTPresentState {
    // 코틀린의 프로퍼티
    // field는 프로퍼티의 데이터를 저장해두는 백킹필드에 대한 레퍼런스
    var name: String? = null
        get() = field?.uppercase() ?: ""
        set(value) {
            if (!value.isNullOrBlank()) {
                field = value
            }
        }

    // type을 String으로만 쓰고 있던 프로젝트에서
    var type: String = "USER"

    // enum화를 시키고 싶으면, wrap, unwrap만 하면 된다
    var userTypeEnum: UserType
        get() = UserType.valueOf(type)
        set(value) {
            type = value.name
        }

    enum class UserType {
        USER,
        ADMIN
    }

    class Tree<T>(
        val left: Tree<T>?,
        val right: Tree<T>?,
        val value: T,
    ) {
        fun isLeaf() = left == null && right == null
    }


    // 프로퍼티로 이런거 하지 말아야 함
    // 동작이 들어간 거임 이건
    val Tree<Int>.sum: Int
        get() = when (this.isLeaf()) {
            true -> value
            false -> this.left!!.sum + value + this.right!!.sum
        }

    // 아래와 같은 경우는 함수를 사용하는것이 좋다
    // 연산 비용이 높거나, 복잡도가 O(1)보다 큰 경우
    // 로깅, 리스너 통지, 값 변경 등 비즈니스로직을 포함하는 경우 함수를 사용
    // 결정적이지 않은 경우 -> 같은 동작 연속 두번 했는데 다른 값이 나올 확률이 있는 경우
    // 변환의 경우 Int.toDouble()
    // getter에서 프로퍼티 상태 변경이 일어나야 하는 경우
    fun Tree<Int>.sum(): Int {
        return when (this.isLeaf()) {
            true -> value
            false -> this.left!!.sum + value + this.right!!.sum
        }
    }

    @Test
    fun `프로퍼티는 동작이 아니라 상태를 나타내야 한다`() {
        val tree = Tree(
            Tree(null, null, 2),
            Tree(
                Tree(null, null, 1),
                Tree(null, null, 2),
                4
            ),
            5
        )
        assertThat(tree.sum).isEqualTo(14)

    }

}