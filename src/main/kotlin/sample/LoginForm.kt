package sample

import org.w3c.dom.Element
import kotlin.browser.document

fun loginForm(): Element {
    val form = document.createElement("form")

    val message = document.createElement("span")
    message.setAttribute("id", "message")

    val username = document.createElement("input")
    username.setAttribute("type", "text")
    username.setAttribute("placeholder", "Username")
    username.setAttribute("id", "username")

    val password = document.createElement("input")
    password.setAttribute("type", "password")
    password.setAttribute("placeholder", "Password")
    password.setAttribute("id", "password")

    val submit = document.createElement("button")
    submit.setAttribute("type", "submit")
    submit.textContent = "Sign In"

    form.append(message, lineBreak(), username, lineBreak(), password, lineBreak(), submit)
    return form
}