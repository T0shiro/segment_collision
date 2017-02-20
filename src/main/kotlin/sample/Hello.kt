package sample

import kotlin.browser.document

fun myApp() {
    val div = document.getElementById("app")
    if (div != null) {
        val form = loginForm()
        div.appendChild(form)
    } else {
        error("Div with id 'app' was not found. Application could not start!")
    }
}