package me.taesu.effectivekotlin.chap2.item17

import org.junit.jupiter.api.Test
import java.time.LocalDate

/**
 * Created by itaesu on 2022/07/08.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseNamedArgument {
    @Test
    fun `이름 있는 아규먼트를 사용하라`() {
        val text = (1..10).joinToString("|")
        // 여기서 "|"는 joinToString을 모르는 사람이면 정확히 알기 어려움. prefix라고 착각할 수 있음.

        val separator = "|"     // 이렇게 표현해준다면 구분자라고 유추가 쉬울 것
        val text2 = (1..10).joinToString(separator)

        // named argument를 사용하면 더욱 좋을 것
        val text3 = (1..10).joinToString(separator = separator)
    }

    @Test
    fun `언제 사용할까`() {
        // 디폴트 아규먼트의 경우
        update(name = "taesu", birth = LocalDate.of(1993, 2, 16), reason = "for update")

        // 같은 타입의 파라미터가 많은 경우
        sendEmail(listOf("taesu@crscube.co.kr"), listOf("taesu@crscube.co.kr"), listOf("taesu@crscube.co.kr"))
        sendEmail(  // 같은 타입이 많으면 아래처럼 이름을 붙이면 좋다
            to = listOf("taesu@crscube.co.kr"),
            cc = listOf("taesu@crscube.co.kr"),
            bcc = listOf("taesu@crscube.co.kr")
        )

        // 함수 타입의 파라미터가 있는 경우
        subscribe(
            onNext = { println(it) },
            onError = { println(it.message) },
            onComplete = { println("finish") }
        )
    }

    fun update(name: String, birth: LocalDate, reason: String = "update") {}
    fun sendEmail(to: List<String>, cc: List<String>, bcc: List<String>) {}
    fun subscribe(
        onNext: (String) -> (Unit),
        onError: (Throwable) -> (Unit),
        onComplete: () -> (Unit)
    ) {
        onNext("test")
        onError(IllegalArgumentException("Err"))
        onComplete()
    }

}