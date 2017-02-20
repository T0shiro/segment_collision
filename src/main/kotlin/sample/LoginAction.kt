package sample

import org.w3c.dom.*
import org.w3c.dom.events.EventListener
import kotlin.dom.appendText
import kotlin.dom.clear

fun processLogin(div: HTMLDivElement, form: HTMLFormElement) {
    val username = form.children["username"] as HTMLInputElement
    val password = form.children["password"] as HTMLInputElement
    if(username.value == "admin" && password.value == "pizza") {
        div.clear()
        div.appendChild(welcomeView())
    } else {
        val message = form.children["message"] as HTMLSpanElement
        message.clear()
        message.appendText("Invalid username or password.")
    }
}

fun loginAction(div: HTMLDivElement) = EventListener {
    it.preventDefault()
    processLogin(div, it.target as HTMLFormElement)
}