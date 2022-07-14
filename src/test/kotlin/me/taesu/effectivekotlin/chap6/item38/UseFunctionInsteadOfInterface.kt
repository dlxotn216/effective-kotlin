package me.taesu.effectivekotlin.chap6.item38

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/14.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseFunctionInsteadOfInterface {
    @Test
    fun `연산 혹은 액션을 전달할 때 인터페이스 대신 함수를 사용하라`() {
        clickListener1(object: OnClick {
            override fun onClick() {
                println("from interface")
            }

        })
        clickListener2 {
            println("from function")
        }
    }

    fun clickListener1(onClick: OnClick) {
        onClick.onClick()
    }

    fun clickListener2(onClick: () -> (Unit)) {
        onClick.invoke()
    }

}

interface OnClick {
    fun onClick()
}