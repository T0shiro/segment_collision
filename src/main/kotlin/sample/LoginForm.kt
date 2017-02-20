package sample

import org.w3c.dom.Element
import kotlin.browser.document

fun loginForm(): Element {
    val form = document.createElement("form")

    val userName = document.createElement("input")
    userName.setAttribute("type", "text")
    userName.setAttribute("placeholder", "Username")

    val password = document.createElement("input")
    password.setAttribute("type", "password")
    password.setAttribute("placeholder", "Password")

    val submit = document.createElement("button")
    submit.setAttribute("type", "submit")
    submit.textContent = "Sign In"

    form.append(userName, lineBreak(), password, lineBreak(), submit)
    return form
}