package me.taesu.effectivekotlin.chap8.item50

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/17.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class ReduceProcessing {
    @Test
    fun `컬렉션 처리 단계 수를 제한하라`() {
         val names1 = listOf(
             Student("Lee"),
             Student("Kim"),
         ).map { it.name }
             .filter { it != null }
             .map { it!! }

        val names2 = listOf(
             Student("Lee"),
             Student("Kim"),
         ).mapNotNull { it.name }

        assertThat(names1).isEqualTo(names2)

        listOf(
             Student("Lee"),
             Student("Kim"),
             null,
         ).filterNotNull() // filter {it != null}.map{it!!}


        listOf(
             1,
             2,
         ).map { it.toString() }.joinToString(",")

        listOf(
             1,
             2,
         ).joinToString { it.toString() }

        listOf(
            1,
            Student("Kim")
        ).filter { it is Student }
            .map { it as Student }

        listOf(
            1,
            Student("Kim")
        ).filterIsInstance<Student>()
    }

}
class Student(val name: String?)