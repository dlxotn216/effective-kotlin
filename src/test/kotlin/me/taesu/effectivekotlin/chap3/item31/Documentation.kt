package me.taesu.effectivekotlin.chap3.item31

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/09.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class Documentation {
    @Test
    fun `문서로 규약을 정의하라`() {
        MyCar().setBreakPedal(23.41)    // 0 ~ 1인지 문서화가 없음
        MyDocumentedCar().setBreakPedal(0.23)
    }

}

interface Car {
    // angle은 뭘 의미할까?
    fun setWheelPosition(angle: Float)

    // 무슨 동작을 하게 해야할까?
    fun setBreakPedal(pressure: Double)
    fun setGasPedal(pressure: Double)
}

class MyCar: Car {
    override fun setWheelPosition(angle: Float) {
        println(angle)
    }

    override fun setBreakPedal(pressure: Double) {
        println(pressure)
    }

    override fun setGasPedal(pressure: Double) {
        println(pressure)
    }
}

// 아래처럼 주석을 달면 각 값의 범위도 알 수 있고 어떤 의미를 뜻하는지도 알 수 있다.
// 문서를 통해 규약을 잘 명세하면 하위 클래스가 다른 동작을 하게 되는 예측 불가한 구현을 만들 가능성이 줄어든다.
// 리스코프 치환 원칙
interface DocumentedCar {
    /**
     * 자동차의 방향을 변경합니다.
     * @param angle 바퀴 축의 각도를 지정합니다.
     * 라디안 단위로 지정하며, 0은 직진을 의미합니다.
     * pi/2는 오른쪽으로 최대로 돌렸을 경우,
     * -pi/2는 왼쪽으로 최대한 돌렸을 경우로 의미합니다.
     * 값은 (-pi/2, pi/2) 범위로 지정해야 합니다.
     */
    fun setWheelPosition(angle: Float)

    /**
     * 자옻아의 속도가 0이 될 때까지 감속합니다.
     *
     * @param pressure 브레이크 폐달을 사용하는 비율을 지정합니다.
     * 0~1 사이의 숫자를 지정합니다. 0은 브레이크를 사용하지 않는 경우,
     * 1은 브레이크를 최대한 사용하는 경우를 의미합니다.
     */
    fun setBreakPedal(pressure: Double)
    fun setGasPedal(pressure: Double)
}

class MyDocumentedCar: DocumentedCar {
    override fun setWheelPosition(angle: Float) {
        println(angle)
    }

    override fun setBreakPedal(pressure: Double) {
        println(pressure)
    }

    override fun setGasPedal(pressure: Double) {
        println(pressure)
    }
}