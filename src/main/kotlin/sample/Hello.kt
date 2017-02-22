package sample

import org.w3c.dom.HTMLDivElement
import kotlin.browser.document

fun myApp() {
    val div = document.getElementById("app") as HTMLDivElement
    val form = loginForm(loginAction(div))
    div.appendChild(form)
}