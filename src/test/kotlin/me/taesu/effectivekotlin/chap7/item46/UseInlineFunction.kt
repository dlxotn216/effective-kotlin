package me.taesu.effectivekotlin.chap7.item46

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseInlineFunction {
    @Test
    fun `함수타입 파라미터를 갖는 함수는 inline으로`() {
        myrepeat(10) {
            println(it)
        }

        // 컴파일하면 아래처럼 대체 됨
        for (index in 0 until 10) {
            println(index)
        }

        // 본문이 아래처럳 대치되므로 reified 한정자를 통해 타입 파라미터를 활용 가능
        printTypeName<Int>()
        // println(Int::class.simpleName)

        printTypeName<String>()
        // println(String::class.simpleName)

        // 비지역적 리턴
        myLoop()

        // 웬만해서 함수타입 파라미터를 받는 경우는 inline으로 선언 해라
    }

    // 컴파일 시점에 함수를 호출하는 부분을 함수의 본문으로 대체하라
    inline fun myrepeat(times: Int, action: (Int) -> Unit) {
        for (index in 0 until times) {
            action(index)
        }
    }

    inline fun <reified T> printTypeName() {
        println(T::class.simpleName)
    }

    inline fun <T> cantPrintTypeName() {
        // println(T::class.simpleName)
    }

    fun myLoop() {
        innerLoop {
            if (it > 5) {
                return
            }
            println("non local return $it")
        }

        println("end my loop")  // 호출되지 않음
    }

    inline fun innerLoop(action: (Int) -> Unit) {
        for (index in 0 until 10) {
            action(index)
        }
    }
}