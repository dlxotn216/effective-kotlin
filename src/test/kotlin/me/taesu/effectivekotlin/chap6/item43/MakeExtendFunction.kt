package me.taesu.effectivekotlin.chap6.item43

import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class MakeExtendFunction {
    @Test
    fun `필수적이지 않은 부분은 확장함수로 만들어라`() {
        val workshop = Workshop(LocalDate.of(2022, 7, 17))
        val event1 = workshop.makeEvent(LocalTime.of(11, 23, 45))
        val event2 = workshop.makeEvent2(LocalTime.of(11, 23, 45))

        // 사용법은 똑같다
        // 단 상황에 맞게 makeEvent가 workshop 클래스 도메인에 필수 API인가를 판단하고 할 것

    }

}

class Workshop(val startDate: LocalDate) {
    fun makeEvent(time: LocalTime) = Event(LocalDateTime.of(startDate, time))
}

fun Workshop.makeEvent2(time: LocalTime) = Event(LocalDateTime.of(startDate, time))

class Event(val at: LocalDateTime)