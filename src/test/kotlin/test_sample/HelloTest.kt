package test_sample

import sample.lineBreak

external class Assert {
    fun ok(actual: Boolean, message: String?): Unit
}

external class QUnit {
    companion object {
        fun test(testName: String, callback: (assert: Assert) -> Unit)
    }
}

fun myApp() {
    QUnit.test("build a linebreak") { assert ->
        assert.ok(lineBreak().tagName == "BR", "Passed!")
    }
}