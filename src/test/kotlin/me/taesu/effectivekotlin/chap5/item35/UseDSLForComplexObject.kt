package me.taesu.effectivekotlin.chap5.item35

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/14.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseDSLForComplexObject {
    @Test
    fun `복잡한 객체를 생성하기 위한 DSL을 만들 것`() {
        val table = table {
            tr {
                td { +"awefawef" }
                td { +"awefawef" }
                td { +"awefawef" }
            }
            tr {
                td { +"awefawef" }
                td { +"awefawef" }
                td { +"awefawef" }
            }
            tr {
                td { +"awefawef" }
                td { +"awefawef" }
                td { +"awefawef" }
            }
        }
        println(
            table.build()
        )
    }

}

class TdBuilder {
    var text = ""
    operator fun String.unaryPlus() {
        text = this
    }

    fun build(): String {
        return "<td>$text</td>"
    }
}

class TrBuilder {
    var html = ""
    val tds = mutableListOf<TdBuilder>()
    fun td(init: TdBuilder.() -> Unit): TdBuilder {
        return TdBuilder().apply {
            init()
            tds += this
        }
    }

    fun build(): String {
        html += "<tr>"
        html += tds.joinToString("") { it.build() }
        html += "</tr>"
        return html
    }
}

class TableBuilder {
    var html = ""
    val trs = mutableListOf<TrBuilder>()
    fun tr(init: TrBuilder.() -> Unit): TrBuilder {
        return TrBuilder().apply {
            init()
            trs += this
        }
    }

    fun build(): String {
        html += "<table>"
        html += trs.joinToString("") { it.build() }
        html += "</table>"
        return html
    }
}

fun table(init: TableBuilder.() -> Unit): TableBuilder {
    return TableBuilder().apply {
        init()
    }
}