package me.taesu.effectivekotlin.chap2.item14

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/02.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class VariableType {
    @Test
    fun `변수타입이 불명확한 경우 명확하게`() {
        // 명확한 변수 타입
        val num = 10
        val name = "taesu"
        val ids = listOf(1, 2, 3, 4)

        val data = getData()    // 타입이 숨겨져 있음
        // IDE로 시그니처 보면 되지 않냐 싶지만 Github 같은데서 코드를 볼 때도 고려 해줘야 한다고 함
        // 휴먼 메모리도 고려해서 여기저기 왔다갔다 하지 않게 해주면 좋다

        // 타입은 무조건 쓰라는건 아니고 아래처럼 불명확한 경우만 쓰자 (상황에 따라서)
        val data2: String = getData()
    }

    fun getData(): String {
        return "test"
    }

}