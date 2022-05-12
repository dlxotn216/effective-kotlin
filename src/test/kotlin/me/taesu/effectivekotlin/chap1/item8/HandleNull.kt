package me.taesu.effectivekotlin.chap1.item8

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/05/12.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class HandleNull {
    @Test
    fun `null을 안전하게 처리하기`() {
        val printer1 = getPrinter(null)
        printer1?.name ?: "Unnamed" // 기본값 사용

        val printer2 = getPrinter("printer2")
        printer2?.name ?: return    // 리턴

        val printer3 = getPrinter("printer3")
        printer3?.name ?: throw IllegalStateException("Unnamed") // 예외 발생
    }

    private fun getPrinter(name: String?): Printer? {
        return name?.let { Printer(it) }
    }

    class Printer(
        val name: String
    )

    @Test
    fun `계약에 의한 스마트 캐스팅`() {
        val list: List<String>? = listOf()
        if (list?.size != 0) {
            // println(list.size) // 컴파일 에러
            println(list?.size) // 컴파일 에러
        }

        // contract에 의한 스마트 캐스팅
        if (!list.isNullOrEmpty()) {
            println(list.size)
        }

        // contract는 아니지만
        // list?.isEmpty()가 Boolean? 이므로 true/false 비교시 null safety 함
        if (list?.isEmpty() == false) {
            println(list.size)
        }
    }

     @Test
    fun `공격적 프로그래밍`() {
         fun send(email: String) {
             println("send to $email")
         }

         // 아래는 방어적으로흘려 간다
         fun sendEmail(user: User) {
             user.email ?: return
             send(user.email)
         }

         // 아래는 공격적으로흘려 간다
         // 개발자에게 오류를 강제로 발생시켜주어 미리 알 수 있게 한다.
         // throw, !!, requireNotNull, checkNotNull을 활용
         // 단, !!는 NPE를 발생시키며 어떠한 설명도 없는 제네릭 예외가 발생해서 아주 확실한 상황에만 쓸 것.
         // !! 연산자가 의미 있게 사용되는 경우는 매우 드물기 때문에 일반적으로는 사용을 피하자.
         fun sendEmail2(user: User) {
             // user.email ?: throw IllegalArgumentException("email was null")
             requireNotNull(user.email) {
                 "email was null"
             }
             send(user.email)
         }
     }
    class User(val email: String?)
}