package sample

import kotlin.browser.document

fun lineBreak() = document.createElement("br")

fun myApp() {
    val div = document.getElementById("app")
    if (div != null) {
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
        div.appendChild(form)
    }
}