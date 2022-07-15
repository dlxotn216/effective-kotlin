package me.taesu.effectivekotlin.chap6.item41

import org.junit.jupiter.api.Test
import java.util.*

/**
 * Created by itaesu on 2022/07/15.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class HowToImplementHashCode {
    @Test
    fun `해시코드 구현하기`() {
        println(User("lee", 12).hashCode())
        println(User("lee", 12).anotherHash())
        println(User("lee", 12).pureKotlinHash())
    }
}

class User(
    val name: String,
    val age: Int
) {
    override fun equals(other: Any?): Boolean {
        return other is User
            && other.name == name
            && other.age == age
    }

    override fun hashCode(): Int {
        var hash = name.hashCode()
        hash += 31 * age.hashCode()
        return hash
    }

    fun anotherHash(): Int {
        // java에만 있으므로 다른 플랫폼은 못씀
        return Objects.hash(name, age)
    }

    fun pureKotlinHash(): Int {
        return pureKotlinHash(name, age)
    }
}

fun pureKotlinHash(vararg fields: Any): Int {
    return fields.fold(0) { acc, any ->
        (acc * 31) + any.hashCode()
    }
}