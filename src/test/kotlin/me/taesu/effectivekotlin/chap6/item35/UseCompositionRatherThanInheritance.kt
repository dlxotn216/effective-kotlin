package me.taesu.effectivekotlin.chap6.item35

import org.junit.jupiter.api.Test

/**
 * Created by itaesu on 2022/07/14.
 *
 * @author Lee Tae Su
 * @version effective-kotlin
 * @since effective-kotlin
 */
class UseCompositionRatherThanInheritance {
    @Test
    fun `상속보단 컴포지션`() {

    }
}

// 상속 제한으로 showAlert같은 플래그가 남발
abstract class LoaderWithProgress(val showAlert: Boolean = false) {
    fun load() {
        // display progress
        innerLoad()
        // hide progress
        if (showAlert) {
            // show alert
        }
    }

    abstract fun innerLoad()
}

class ProfileLoader: LoaderWithProgress(true) {
    override fun innerLoad() {
        // load profile
    }
}

class ImageLoader: LoaderWithProgress(false) {
    override fun innerLoad() {
        // load image
    }
}


// 컴포지션을 통해 조립을 통한 재사용 가능
// 상속은 모든걸 가져와야 함
// 만약 하위 타입이 되어야 하는 등의 상속이 필요하면 -> delegate를 사용
class Progress {
    fun showProgress() {
        // display progress
    }

    fun hideProgress() {
        // hide progress
    }
}

class Alert {
    fun showAlert() {
        // show
    }
}

class ProfileLoader2 {
    val progress = Progress()
    val alert = Alert()
    fun load() {
        progress.showProgress()
        // load profile
        progress.hideProgress()
        alert.showAlert()
    }
}

class ImageLoader2 {
    val progress = Progress()
    fun load() {
        progress.showProgress()
        // load profile
        progress.hideProgress()
    }
}

class CounterSet(private val _innerSet: MutableSet<Int>): MutableSet<Int> by _innerSet {
    var added: Int = 0
    override fun add(element: Int): Boolean {
        added++
        return _innerSet.add(element)
    }

    override fun addAll(elements: Collection<Int>): Boolean {
        added += elements.size
        return _innerSet.addAll(elements)
    }
}