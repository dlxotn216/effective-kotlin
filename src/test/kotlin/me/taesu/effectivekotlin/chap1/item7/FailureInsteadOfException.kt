package me.taesu.effectivekotlin.chap1.item7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/05/10.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class FailureInsteadOfException {
    @Test
    fun `결과 부족이 발생할 경우 null or failure를 사용할 것`() {
        val success = SnapshotFileCreateService().createSnapshot("1")
        val failure = SnapshotFileCreateService().createSnapshot("2")

        // 예외가 던져져야 하는 경우 (Spring Transaction Rollback)엔 여기서 던지면 될 듯?
        when (success) {
            is Success -> println(success.result)
            is Failure -> println(success.debugMessage)
        }

        when (failure) {
            is Success -> println(failure.result)
            is Failure -> println(failure.debugMessage)
        }

        assertThat(success).isInstanceOf(Success::class.java)
        assertThat(failure).isInstanceOf(Failure::class.java)
    }
}

sealed class Result<out T>
class Success<out T>(val result: T): Result<T>()
class Failure<out T>(
    val throwable: Throwable,
    val debugMessage: String
): Result<T>()

// Spring MVC의 Service에서 아래처럼 써도 될까?
// DocumentCreateService가 여러 서비스에 의존하는 상황에서 각각 서비스가 Failure를 리턴한다고 하면
// 실패 상황에 대한 처리 하는 코드는 추가 될듯.
// 명확하게 성공, 실패에 대한 흐름을 한눈에 볼 수는 있겠으나 대부분의 실패는 발생한 상황에서 후속 시나리오가 진행 불가한 상황이라
// 예외 던져서 ExceptionHandler로 위임하는게 나을수도?

// -> 예외를 정보 전달 방법으로 사용하지 말 것.
// 잘못된 특별한 상황을 나타내야 하며 처리되어야 함.
// 예외는 예외적인 상황일 때 던지는 것이 좋다.
class DocumentCreateService(private val snapshotFileCreateService: SnapshotFileCreateService) {
    fun transactional(arg: String): Success<String> {
        // repository.save(arg)
        return when (val it = snapshotFileCreateService.createSnapshot(arg)) {
            is Success -> it
            is Failure -> throw it.throwable
        }
    }
}


class SnapshotFileCreateService {
    // Result (Success, Failure)를 통해서 성공/실패 응답을 내보내기
    // 예외를 사용하지 않도록
    fun createSnapshot(arg: String): Result<String> {
        return when (arg) {
            "1" -> Success("urn:snapshot-v3:$arg")
            else -> Failure(
                SnapshotFileCreateFailureException(),
                "[$arg] is invalid"
            )
        }
    }

    // 실패시 null을 리턴하기. 단 반드시 null을 리턴할 수 있다는 경고를 주기 위해 메서드 이름에 OrNull을 붙여주기
    fun createSnapshotOrNull(arg: String): String? {
        return when (arg) {
            "1" -> "urn:snapshot-v3:$arg"
            else -> null
        }
    }
}

class SnapshotFileCreateFailureException(): RuntimeException()