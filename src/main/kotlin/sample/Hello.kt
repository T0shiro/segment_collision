package sample

import org.w3c.dom.HTMLDivElement
import kotlin.browser.document

fun myApp() {
    val div = document.getElementById("app") as HTMLDivElement
    if (div != null) {
        val form = loginForm()
        form.addEventListener("submit", loginAction(div))
        div.appendChild(form)
    } else {
        error("Div with id 'app' was not found. Application could not start!")
    }
}