package me.taesu.effectivekotlin.chap1.item9

import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader

/**
 * Created by itaesu on 2022/05/31.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseFunction {
    @Test
    fun `Use를 사용하어 리소스를 닫아라`() {
        BufferedReader(FileReader("src/test/resources/exam/test.txt")).use { reader ->
            reader.lines().forEach {
                println(it)
            }
        }
    }

    @Test
    fun `UseLines는 메모리에 하나의 라인만 유지하므로 대용량 파일에 유리`() {
        BufferedReader(FileReader("src/test/resources/exam/test.txt")).useLines { sequence ->
            sequence.forEach {
                println(it)
            }
        }
    }

     @Test
    fun `UseLines는 시퀀스를 반환하므로 지나간 행을 읽으려면 다시 파일을 열어야 한다`() {
        BufferedReader(FileReader("src/test/resources/exam/test.txt")).useLines { sequence ->
            println(sequence.sumOf { it.length })
        }
    }


}