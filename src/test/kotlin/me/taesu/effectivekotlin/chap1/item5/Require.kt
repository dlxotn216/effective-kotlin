package me.taesu.effectivekotlin.chap1.item5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Created by itaesu on 2022/05/09.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class Require {
    @Test
    fun `Require 블록으로 argument 검증`() {
        fun factorial(n: Int): Long {
            require(n >= 0)
            return if (n <= 1) 1 else factorial(n - 1) * n
        }

        assertThat(factorial(5)).isEqualTo(120)
        assertThat(factorial(1)).isEqualTo(1)
        assertThrows<IllegalArgumentException> {
            factorial(-1)
        }

        fun isValidEmail(email: String): Boolean {
            return email.contains("@")
        }

        fun sendEmail(user: User) {
            requireNotNull(user.email) {
                "Email will be not null"
            }
            require(isValidEmail(user.email)) {
                "Invalid email"
            }
        }

        val nullEmail = assertThrows<IllegalArgumentException> { sendEmail(User(null)) }
        assertThat(nullEmail.message).isEqualTo("Email will be not null")

        val invalidEmail = assertThrows<IllegalArgumentException> { sendEmail(User("taesu")) }
        assertThat(invalidEmail.message).isEqualTo("Invalid email")
        sendEmail(User("taesu@crscube.co.kr")) // any exception occur.
    }

    @Test
    fun `Custom Require 블록으로 argument 검증`() {
        fun factorial(n: Int): Long {
            require(n >= 0, ErrorCode.REQUIRED_PARAM) {
                "N이 0보다 작습니다."
            }
            return if (n <= 1) 1 else factorial(n - 1) * n
        }

        assertThat(factorial(5)).isEqualTo(120)
        assertThat(factorial(1)).isEqualTo(1)
        assertThrows<InvalidRequestException> {
            factorial(-1)
        }

        fun isValidEmail(email: String): Boolean {
            return email.contains("@")
        }

        fun sendEmail(user: User) {
            requireNotNull(user.email, ErrorCode.INVALID_PARAM) {
                "Email will be not null"
            }
            require(isValidEmail(user.email), ErrorCode.INVALID_PARAM) {
                "Invalid email"
            }
        }

        val nullEmail = assertThrows<InvalidRequestException> { sendEmail(User(null)) }
        assertThat(nullEmail.message).isEqualTo("Email will be not null")

        val invalidEmail = assertThrows<InvalidRequestException> { sendEmail(User("taesu")) }
        assertThat(invalidEmail.message).isEqualTo("Invalid email")
        sendEmail(User("taesu@crscube.co.kr")) // any exception occur.
    }

    class User(
        val email: String?
    )
}

@OptIn(ExperimentalContracts::class)
inline fun <T: Any> requireNotNull(
    value: T?,
    errorCode: ErrorCode,
    lazyMessage: () -> String
): T {
    contract {
        returns() implies (value != null)
    }

    if (value == null) {
        throw InvalidRequestException(errorCode, lazyMessage())
    } else {
        return value
    }
}

@OptIn(ExperimentalContracts::class)
inline fun require(
    value: Boolean,
    errorCode: ErrorCode,
    lazyMessage: () -> String
) {
    contract {
        returns() implies value
    }
    if (!value) {
        throw InvalidRequestException(errorCode, lazyMessage())
    }
}

class InvalidRequestException(
    errorCode: ErrorCode,
    message: String,
): RuntimeException(message)

enum class ErrorCode {
    REQUIRED_PARAM,
    INVALID_PARAM,
}