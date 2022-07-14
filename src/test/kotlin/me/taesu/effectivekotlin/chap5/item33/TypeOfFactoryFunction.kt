package me.taesu.effectivekotlin.chap5.item33

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/14.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class MyIntArrayList {
    // (1) Companion object를 활용한 팩토리 함수
    companion object {
        private val _instance = MyIntArrayList()

        // 파라미터를 하나 받는
        fun from(element: Int) = arrayListOf(element)

        // of -> 파라미터 여러개를 받고
        fun of(vararg elements: Int) = arrayListOf(elements)

        // from과 비슷하나 조금더 의미를 부여함? 둘중 하나만 써도 될 듯
        fun valueOf(element: Int) = element.toString()

        // 싱글턴으로 인스턴스 리턴 하기
        fun instance() = _instance
        fun getInstance() = _instance

        // 싱글턴이 아닌 새로운 인스턴스 리턴
        fun createInstance() = MyIntArrayList()
        fun newInstance() = MyIntArrayList()
    }
}

// (2) 톱 레벨 함수를 활용한 팩토리 함수
// listOf, arrayListOf 등이 여기에 해당
// public 톱레벨 함수는 어디서든 참조 가능하므로 남발 하면 IDE의 인텔리센스를 복잡하게 할 수 있으므로 신중하게 선언
fun from(element: Int) = arrayListOf(element)

// (3) 가짜 생성자 -> 톱레벨 함수로 선언하는 것이 좋다
inline fun <T> List(size: Int, fillWith: () -> T): List<T> {
    return (1..size).map { fillWith() }
}

// (4) 팩토리 클래스 활용
data class Student(
    val key: Long,
    val name: String
)
class MyFactory {
    var nextKey = 0L    // 프로퍼티를 가질 수 있다는 장점이 있다
    fun createStudent(name: String) = Student(++nextKey, name)
}

// 보통은 companion을 자주 사용하고 톱레벨에 선언하는 팩토리 함수는 신중히 이름을 지어야 한다.
class TypeOfFactoryFunction {
    @Test
    fun `가짜 생성자 테스트`() {
        assertThat(List(50) { 1 }.size).isEqualTo(50)
        assertThat(List(50) { 1 }.all { it == 1 }).isEqualTo(true)

        // 이미 있음
        kotlin.collections.List(3) {"User$it"}
    }

}
