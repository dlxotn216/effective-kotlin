package me.taesu.effectivekotlin.chap3.item23

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/08.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class PreventTypeParameterShadowing {
    // 프로퍼티와 파라미터의 섀도잉
    class Forest(val name: String) {
        fun addTree(name: String) {
            println(name)
            println(this.name)
        }
    }

    interface Tree
    class Birch: Tree
    class Spruce: Tree

    // 타입 파라미터의 섀도잉
    class Forest2<T: Tree> {
        val trees: MutableList<T> = mutableListOf()
        fun <T: Tree> addTree(tree: T) {
            // ...
            // trees.add(tree)
        }

        fun <ST: Tree> `진짜로 의도한 것 이라면 독립적으로 분리`(tree: ST) {
            // ...
            // trees.add(tree)
        }
    }
    @Test
    fun `타입 파라미터의 섀도잉을 피하라`() {
        val birchForest = Forest2<Birch>()
        birchForest.addTree(Birch())
        birchForest.addTree(Spruce())   // 함수가 클래스의 타입파라미터 T를 섀도잉
    }
}