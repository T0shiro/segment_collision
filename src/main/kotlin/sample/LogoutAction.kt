package sample

import org.w3c.dom.*
import org.w3c.dom.events.EventListener
import kotlin.dom.clear

fun processLogout(div: HTMLDivElement) {
    div.clear()
    div.appendChild(loginForm(loginAction(div)))
}

fun logoutAction(div: HTMLDivElement) = EventListener {
    it.preventDefault()
    processLogout(div)
}