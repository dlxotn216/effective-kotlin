package me.taesu.effectivekotlin.chap7.item45

import org.junit.jupiter.api.Test
import java.math.BigInteger

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class AvoidUnNecessaryObject {
    @Test
    fun `불필요한 객체 생성 삼가하라`() {
        // 가능하면 기본 자료형을 사용 할 것
        // kotlin   java
        // Int   -> Int
        // Int?  -> Integer
        // List<Int> -> List<Integer>
    }

}

// 순수함수는 캐싱 활용 가능 -> 메모이제이션
// 이미 계산 된 피보나치가 바로 구해진다.
private val FIB_CACHE = mutableMapOf<Int, BigInteger>()
fun fib(n: Int): BigInteger =
    FIB_CACHE.getOrPut(n) {
        if (n <= 1) BigInteger.ONE else fib(n - 1) + fib(n - 2)
    }

fun String.isValidIpAddress(): Boolean {
    // 매우매우 무거운 정규표현식이라면? 차라리 외부로 뺄 것
    return this.matches("\\A(?:awefa...wa.faw.ef.awf.aw.f.".toRegex())
}