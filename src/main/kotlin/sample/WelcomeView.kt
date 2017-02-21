package sample

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLSpanElement
import org.w3c.dom.events.EventListener
import kotlin.browser.document
import kotlin.dom.appendText

fun welcomeView(listener: EventListener): HTMLDivElement {
    val view = document.createElement("div") as HTMLDivElement
    view.setAttribute("id", "welcome")

    val message = document.createElement("span") as HTMLSpanElement
    message.setAttribute("id", "message")
    message.appendText("Welcome!")

    val button = document.createElement("button")
    button.textContent = "Sign Out"
    button.setAttribute("id", "signout")
    button.addEventListener("click", listener)

    view.append(message, lineBreak(), button)
    return view
}