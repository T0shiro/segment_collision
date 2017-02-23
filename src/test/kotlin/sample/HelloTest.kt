package sample

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSpanElement
import org.w3c.dom.events.EventListener
import org.w3c.dom.get
import kotlin.browser.document

fun myApp() {
    val div = document.createElement("div") as HTMLDivElement
    val listener = EventListener {}

    QUnit.test("Build a linebreak") { assert ->
        assert.equal(lineBreak().tagName, "BR", "BR tag OK!")
    }

    QUnit.test("Build a login form") { assert ->
        val form = loginForm(loginAction(div))
        assert.equal(form.tagName, "FORM", "FORM tag OK!")

        assert.equal(form.getElementsByTagName("INPUT").length, 2, "INPUT elements OK!")
        assert.equal(form.getElementsByTagName("BUTTON").length, 1, "BUTTON element OK!")

        assert.ok(form.children["username"] != null, "Username OK!")
        assert.ok(form.children["password"] != null, "Password OK!")
    }

    QUnit.test("Build a welcome view") { assert ->
        val view = welcomeView(listener)
        assert.equal(view.tagName, "DIV", "DIV tag OK!")
        assert.equal(view.getAttribute("id"), "welcome", "DIV id OK!")

        assert.ok(view.children["message"] != null, "Message OK!")
        assert.ok(view.children["signout"] != null, "Signout OK!")
    }

    QUnit.test("Process invalid login") { assert ->
        val form = loginForm(listener)
        processLogin(div, form)
        val message = form.children["message"] as HTMLSpanElement
        assert.equal(message.textContent, "Invalid username or password.", "Login error message OK!")
    }

    QUnit.test("process valid login") { assert ->
        val form = loginForm(listener)
        val username = form.children["username"] as HTMLInputElement
        username.value = "admin"
        val password = form.children["password"] as HTMLInputElement
        password.value = "pizza"

        processLogin(div, form)
        assert.ok(div.children["welcome"] != null, "Welcome view OK!")
    }

    QUnit.test("process a logout") { assert ->
        processLogout(div)
        assert.ok(div.children["loginform"] != null, "Login form found!")
    }

}