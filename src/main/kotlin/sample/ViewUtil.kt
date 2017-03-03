package sample

import org.w3c.dom.HTMLBRElement
import kotlin.browser.document

fun lineBreak() = document.createElement("br") as HTMLBRElement