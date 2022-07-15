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
        println(User("lee", 14).hashCode())

        println()
        println(User("lee", 12).anotherHash())
        println(User("lee", 14).anotherHash())

        println()
        println(User("lee", 12).pureKotlinHash())
        println(User("lee", 14).pureKotlinHash())

        // 동일한 해시코드가 나옴
        println()
        println(User("lee", 12).thisClassHashCode())
        println(User("lee", 14).thisClassHashCode())

        println()
        val list = List(1000) { SameHashCode("$it") }
        println(SameHashCode.equalCount)
        println(SameHashCode.hashCount)

        println("\nafter to set")
        SameHashCode.equalCount = 0
        SameHashCode.hashCount = 0

        val toSet = list.toMutableSet()
        println(SameHashCode.equalCount)    // 508319
        println(SameHashCode.hashCount)     // 1000

        println("\nafter in")
        SameHashCode.equalCount = 0
        SameHashCode.hashCount = 0

        SameHashCode("686") in toSet
        println(SameHashCode.equalCount)    // 391
        println(SameHashCode.hashCount)
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

    fun thisClassHashCode(): Int {
        return this::class.hashCode()
    }
}

fun pureKotlinHash(vararg fields: Any): Int {
    return fields.fold(0) { acc, any ->
        (acc * 31) + any.hashCode()
    }
}

class SameHashCode(
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        equalCount++
        return other is SameHashCode && other.name == name
    }

    override fun hashCode(): Int {
        hashCount++
        return 13
    }

    companion object {
        var equalCount = 0
        var hashCount = 0
    }
}