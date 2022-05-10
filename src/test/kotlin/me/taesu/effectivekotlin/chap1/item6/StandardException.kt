package me.taesu.effectivekotlin.chap1.item6

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/05/10.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class StandardException {
    @Test
    fun `사용자 정의 예외보다는 표준 예외를 사용하라`() {
        // bad
        UserRepository().findByKey(1L) ?: throw ResourceNotFoundException()

        // good
        UserRepository().findByKey(1L) ?: throw NoSuchElementException()
    }

}
class ResourceNotFoundException(): RuntimeException()
class UserRepository {
    fun findByKey(key: Long): String? = key.toString()
}