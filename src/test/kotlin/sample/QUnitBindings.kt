package sample

@JsModule("qunit")
external abstract class Assert {
    fun ok(actual: Boolean, message: String): Unit
    fun <T> equal(actual: T, expected: T, message: String): Unit
}

@JsModule("qunit")
external abstract class QUnit {
    companion object {
        fun test(testName: String, callback: (assert: Assert) -> Unit)
    }
}