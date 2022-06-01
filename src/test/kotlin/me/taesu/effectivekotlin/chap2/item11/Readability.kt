package me.taesu.effectivekotlin.chap2.item11

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.util.zip.ZipInputStream

/**
 * Created by itaesu on 2022/06/01.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class Readability {
    @Test
    fun `가독성 있게 설계하라`() {
        // given
        val user = User(21)
        assertThat(process1(user)).isEqualTo("is adult")
        assertThat(process2(user)).isEqualTo("is adult")

        val user2 = User(19)
        assertThat(process1(user2)).isEqualTo("show error")
        assertThat(process2(user2)).isEqualTo("show error")

        assertThat(process1(null)).isEqualTo("show error")
        assertThat(process2(null)).isEqualTo("show error")
    }

    fun process1(user: User?): String {
        return if (user != null && user.isAdult) {
            "is adult"
        } else {
            "show error"
        }
    }

    // process 1과 사실은 똑같은 일을하는 코드인데 예측이 힘들다
    fun process2(user: User?): String {
        return user?.takeIf {
            it.isAdult
        }?.let {
            "is adult"
        } ?: "show error"
    }


    @Test
    fun `가독성 있게 설계하라 else에 더 추가 된다면`() {
        // given
        val user = User(21)

        /*
        is adult
        afterSuccess
         */
        process3(user)

        /*
        is adult
        afterSuccess
        show error
        afterError
         */
        process4(user)
    }

    fun process3(user: User?) {
        if (user != null && user.isAdult) {
            println("is adult")
            afterSuccess()
        } else {
            println("show error")
            afterError()
        }
    }

    // process 3과 동일한 일을 할까?
    fun process4(user: User?) {
        user?.takeIf {
            it.isAdult
        }?.let {
            println("is adult")
            afterSuccess()      // 여기서 특정 상황에 따라 null이 반환된다면?
        } ?: run {              // else 절을 대체하는 run
            println("show error")
            afterError()
        }
    }

    fun afterSuccess(): String? {
        println("afterSuccess")
        return null
    }

    fun afterError(): String? {
        println("afterError")
        return null
    }

    @Test
    fun `let의 활용법`() {
        var user: User? = getUser()

        // nullable한 가변 프로퍼티가 존재하여 스마트캐스팅이 불가능한 경우
        user?.let {
            println(it.isAdult)
        }

        // 특정 연산을 최종 가공 후로 옮기려 할 때
        val users = listOf(User(12), User(25))
        println(users.filter { it.isAdult }.map { it.age }.joinToString(","))
        users.filter { it.isAdult }.map { it.age }.joinToString(",").let { println(it) }
        users.filter { it.isAdult }.map { it.age }.joinToString(",").let(::println)

        // 데코레이터를 통해 객체를 감쌀때
        // 4번의 객체를 감싸는 동안 코드 작성 흐름이 깨짐
        ObjectInputStream(
            ZipInputStream(
                BufferedInputStream(
                    FileInputStream("src/test/resources/exam/test.txt")
                )
            )
        ).use {
            // ...
        }

        // let 이용하면
        FileInputStream("src/test/resources/exam/test.txt")
            .let(::BufferedInputStream)
            .let(::ZipInputStream)
            .let(::ObjectInputStream)
            .use {
                // 보기도 더 깔끔함
            }
    }

    private fun getUser(): User? {
        val user: User?
        user = User(20)
        return user
    }


    class User(val age: Int) {
        val isAdult get() = age >= 20
    }

}