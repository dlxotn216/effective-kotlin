package me.taesu.effectivekotlin.chap1.item5

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Created by itaesu on 2022/05/09.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class SmartCast {
    @Test
    fun `Require, Check 블록으로 검증된 경우 스마트 캐스팅 된다`() {

        fun send(email: String) {
            println("send to $email")
        }

        fun sendEmail(user: User) {
            // val before: String = user.email // compile error
            val before: String? = user.email
            requireNotNull(user.email, ErrorCode.INVALID_PARAM) {
                "Email will be not null"
            }
            val after: String = user.email
            send(after)
        }

         fun sendEmailWithThrow(user: User) {
            val email = user.email ?: throw InvalidRequestException(ErrorCode.REQUIRED_PARAM, "User email was null")
            send(email)
        }

        fun sendEmailWithoutThrow(user: User) {
            val email = user.email ?: return
            send(email)
        }

        fun sendEmailWithoutThrow2(user: User) {
            val email = user.email ?: run {
                // run 블록을 활용해 로그도 남길 수 있다
                println("Email not sent. $user email empty")
                return
            }
            send(email)
        }

        assertThrows<InvalidRequestException> {
            sendEmail(User(null))
        }
        assertThrows<InvalidRequestException> {
            sendEmailWithThrow(User(null))
        }
        sendEmailWithoutThrow(User(null))
        sendEmailWithoutThrow2(User(null))
    }

    class User(
        val email: String?
    )

}