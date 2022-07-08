package me.taesu.effectivekotlin.chap3.item24

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/08.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class GenericTypeAndVariance {
    class Cup<T>

    @Test
    fun `제네릭타입과 variance 한정자 미사용`() {
        val intCup = Cup<Int>()
        val numberCup = Cup<Number>()

        // T에 아무런 variance 한정자가 없으므로 두 타입은 관련성이 없다
        // val errorCup: Cup<Number> = Cup<Int>
    }

    open class Dog
    class Puppy: Dog()
    class Hound: Dog()

    class MyOutCup<out T>
    class MyInCup<in T>

    @Test
    fun `제네릭타입과 variance 한정자 사용`() {
        val dogCup: MyOutCup<Dog> = MyOutCup<Puppy>()
        // val dogCup2: MyOutCup<Puppy> = MyOutCup<Dog>()

        val inCup: MyInCup<Puppy> = MyInCup<Dog>()
        // val inCup2: MyInCup<Dog> = MyInCup<Puppy>()

        //  invariant A<T> ->  아무 연관 없음
        //  covariant A<out T>    -> val out: A<Number> = A<Int>
        //  contravariant A<in T> -> val _in: A<Int> = A<Number>
    }

    @Test
    fun `함수타입의 제네릭에서 variance`() {
        fun my(lambda: (Int) -> (Any)) {

        }
        // 함수 타입의 모든 파라미터타입은 contravariant

        // 함수 타입의 모든 리턴타입은 covariant
        // Any = Double, Long, String
        val intToDouble: (Int) -> Double = { it.toDouble() }
        my(intToDouble)

        val intToLong: (Int) -> Long = { it.toLong() }
        my(intToLong)

        val intToString: (Int) -> String = { it.toString() }
        my(intToString)
    }

    @Test
    fun `파라미터 타입이 에측 가능하다면`() {
        takeDog(Dog())
        takeDog(Puppy())
        takeDog(Hound())
    }

    fun takeDog(dog: Dog) {

    }

    // class DogBoxError<out T> {
    //     var value: T? = null // error
    //     // 코틀린에선 public in 한정자 위치에 out이 오는걸 막고 있다
    //     fun takeDog(dog: T) {    // error
    //
    //     }
    // }

    class DogBox<in T> {
        // 코틀린에선 public in 한정자 위치에 out이 오는걸 막고 있다
        fun takeDog(dog: T) {

        }
    }

    // private으로 한정되면 가능
    class DogBoxErrorPrivate<out T> {
        private var value: T? = null // error

        // 코틀린에선 public in 한정자 위치에 out이 오는걸 막고 있다
        private fun takeDog(dog: T) {    // error

        }
    }

    @Test
    fun `파라미터 타입이 에측 불가능하다면`() {
        // val puppyBox = DogBoxError<Puppy>()
        // val dogBox: DogBoxError<Dog> = puppyBox
        // // dogBox.takeDog(Hound())     // 위험한 코드가 됨
        // val anyBox: DogBoxError<Any> = puppyBox
        // anyBox.takeDog(23)          // 이게 가능해져 버림

        val puppyBox1 = DogBox<Puppy>()
        // val dogBox1: DogBox<Dog> = puppyBox1    // in 이므로 에러
        // dogBox1.takeDog(Hound())
    }
}