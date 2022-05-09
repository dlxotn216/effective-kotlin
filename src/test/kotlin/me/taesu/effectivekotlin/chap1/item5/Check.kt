package me.taesu.effectivekotlin.chap1.item5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Created by itaesu on 2022/05/09.
 *
 * @author Lee Tae Su
 * @version ConsentV3 v1.0 wB202203
 * @since ConsentV3 v1.0 wB202203
 */
class Check {
    @Test
    fun `Check 문으로 상태 검증`() {
        val document = Document()
        document.ongoing("taesu@crscube.co.kr")

        val exception = assertThrows<IllegalStateException> {
            document.ongoing("taesu")
        }
        assertThat(exception.message).isEqualTo("ONGOING 인 문서는 시작 불가능합니다.")
    }

    class Document {
        var status: DocumentStatus = DocumentStatus.INITIAL
        private set

        fun ongoing(modifiedBy: String?) {
            requireNotNull(modifiedBy, ErrorCode.REQUIRED_PARAM) {
                "modifiedBy는 피수입니다."
            }

            // 함수 전체에 대한 예측이 있을 땐 일반적으로 require 블록 뒤에
            check(status != DocumentStatus.INITIAL) {
                "$status 인 문서는 시작 불가능합니다."
            }
            this.status = DocumentStatus.ONGOING
        }
    }
    enum class DocumentStatus {
        INITIAL,
        ONGOING,
        COMPLETE
    }

}