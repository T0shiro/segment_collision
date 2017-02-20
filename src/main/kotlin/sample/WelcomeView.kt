package sample

import org.w3c.dom.HTMLSpanElement
import kotlin.browser.document
import kotlin.dom.appendText

fun welcomeView(): HTMLSpanElement {
    val span = document.createElement("span") as HTMLSpanElement
    span.setAttribute("id", "message")
    span.appendText("Welcome!")
    return span
}