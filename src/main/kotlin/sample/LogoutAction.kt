package sample

import org.w3c.dom.*
import org.w3c.dom.events.EventListener
import kotlin.dom.clear

fun logoutAction(div: HTMLDivElement) = EventListener {
    it.preventDefault()
    div.clear()
    div.appendChild(loginForm(loginAction(div)))
}