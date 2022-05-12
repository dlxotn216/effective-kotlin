package me.taesu.effectivekotlin.chap1.item8

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

/**
 * Created by itaesu on 2022/05/12.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class LateInitAndNotNullDelegate {
    private var repository: UserRepository? = null
    private lateinit var repository2: UserRepository
    // private lateinit var count: Int      // 기본 타입은 lateinit 불가
    private var count: Int by Delegates.notNull()   // Delegates.notNull을 사용할 것
    @BeforeEach
    fun init () {
        repository = UserRepository()
        repository2 = UserRepository()
        count = 0

    }

    @Test
    fun `lateinit 프로퍼티와 notNull 델리데이트`() {
        // 매번 nullability를 체크하는것은 바람직 하지 않다. 의미가 없는 코드.
        repository!!.existsByEmail("test")

        // 대신 lateinit을 사용하자
        // lateinit은 사용 직전에 초기화 여부를 본다.
        // 런타임에 예외가 발생하므로 위험할 수 있으니 반드시 사용전 초기화 되어있는 경우에만 사용할 것.
        repository2.existsByEmail("test")
    }

    class UserRepository {
        fun existsByEmail(email: String): Boolean = true
    }
}