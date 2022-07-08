package me.taesu.effectivekotlin.chap3.item20

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/08.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class DontRepeatGeneralAlgorithm {
    @Test
    fun `일반적인 알고리즘을 반복 구현하지 마라`() {
        // 즉,  stdlib를 잘 파악할것

        assertThat(getPercent(120)).isEqualTo(100)
        assertThat(getPercent(50)).isEqualTo(50)
        assertThat(getPercent(-10)).isEqualTo(0)

        assertThat((120).coerceIn(0, 100)).isEqualTo(100)
        assertThat((50).coerceIn(0, 100)).isEqualTo(50)
        assertThat((-10).coerceIn(0, 100)).isEqualTo(0)
    }

    private fun getPercent(n: Int): Int {
        return when {
            n > 100 -> 100
            n < 0 -> 0
            else -> n
        }
    }

    @Test
    fun `표준 라이브러리 살펴보기`() {
        val sources = mutableListOf<SourceEntity>()
        listOf(
            SourceResponse(1, "도서", "대한민국"),
            SourceResponse(2, "도서", "미국"),
            SourceResponse(3, "도서", "일본"),
        ).forEach {
            val entity = SourceEntity()
            entity.id = it.id
            entity.category = it.category
            entity.country = it.country
            sources.add(entity)
        }

        listOf(
            SourceResponse(4, "도서", "대한민국"),
            SourceResponse(5, "도서", "미국"),
            SourceResponse(6, "도서", "일본"),
        ).map(::toEntity)
    }

    private fun toEntity(source: SourceResponse) = SourceEntity().apply {
        this.id = source.id
        this.category = source.category
        this.country = source.country
    }

    class SourceResponse(
        val id: Int,
        val category: String,
        val country: String,
    )

    class SourceEntity {
        var id: Int = 0
        var category: String = ""
        var country: String = ""
    }

    @Test
    fun `나만의 유틸리티 만들기`() {
        // 컬렉션의 모든 요소 곱이 필요하면?
        fun Iterable<Int>.product() = fold(1) { acc, element -> acc * element }

        assertThat(listOf(1, 2, 3, 4, 5).product()).isEqualTo(120)

        // 확장함수가 좋을까 유틸함수가 좋을까 (어쨌든 확장함수도 스태틱이지만)
        TextUtils.isBlank("test")
        "test".isBlank()

        // 확장함수 자동완성 기능의 효과를 본다. 유틸 함수는 TextUtils의 존재를 알아야 쓸 수 있게 된다.
    }

    object TextUtils {
        fun isBlank(value: String): Boolean {
            return value.trim().isEmpty()
        }
    }

    fun String.isBlank() = this.trim().isEmpty()

}