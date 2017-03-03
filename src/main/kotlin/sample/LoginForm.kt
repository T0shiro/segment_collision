package sample

import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSpanElement
import org.w3c.dom.events.EventListener
import kotlin.browser.document

fun loginForm(listener: EventListener): HTMLFormElement {
    val form = document.createElement("form") as HTMLFormElement
    form.id = "loginform"

    val message = document.createElement("span") as HTMLSpanElement
    message.id = "message"

    val username = document.createElement("input") as HTMLInputElement
    username.type = "text"
    username.placeholder = "Username"
    username.id = "username"

    val password = document.createElement("input") as HTMLInputElement
    password.type = "password"
    password.placeholder = "Password"
    password.id = "password"

    val submit = document.createElement("button") as HTMLButtonElement
    submit.type = "submit"
    submit.textContent = "Sign In"

    form.append(message, lineBreak(), username, lineBreak(), password, lineBreak(), submit)
    form.addEventListener("submit", listener)
    return form
}