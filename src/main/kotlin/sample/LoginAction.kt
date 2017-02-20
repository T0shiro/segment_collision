package sample

import org.w3c.dom.*
import org.w3c.dom.events.EventListener
import kotlin.browser.document
import kotlin.dom.appendText
import kotlin.dom.clear

fun formAction(div: Element) = EventListener {
    it.preventDefault()
    val form = it.target as HTMLFormElement
    val username = form.children["username"] as HTMLInputElement
    val password = form.children["password"] as HTMLInputElement
    if(username.value == "admin" && password.value == "pizza") {
        div.clear()
        val span = document.createElement("span")
        span.appendText("Welcome!")
        div.appendChild(span)
    } else {
        val message = form.children["message"] as HTMLSpanElement
        message.clear()
        message.appendText("Invalid username or password.")
    }
}