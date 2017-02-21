package test_sample

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSpanElement
import org.w3c.dom.events.EventListener
import org.w3c.dom.get
import sample.*
import kotlin.browser.document

fun myApp() {
    val div = document.createElement("div") as HTMLDivElement
    val listener = EventListener {}

    QUnit.test("build a linebreak") { assert ->
        assert.ok(lineBreak().tagName == "BR", "BR tag verified!")
    }

    QUnit.test("build a login form") { assert ->
        val form = loginForm(loginAction(div))
        assert.ok(form.tagName == "FORM", "FORM tag verified!")

        assert.ok(form.getElementsByTagName("INPUT").length == 2, "INPUT count verified!")
        assert.ok(form.getElementsByTagName("BUTTON").length == 1, "BUTTON count verified!")

        assert.ok(form.children["username"] != null, "Username found!")
        assert.ok(form.children["password"] != null, "Password found!")
    }

    QUnit.test("build a welcome view") { assert ->
        val view = welcomeView(listener)
        assert.ok(view.tagName == "DIV", "DIV tag verified!")
        assert.ok(view.getAttribute("id") == "welcome", "DIV id verified!")

        assert.ok(view.children["message"] != null, "Message found!")
        assert.ok(view.children["signout"] != null, "Signout found!")
    }

    QUnit.test("process invalid login") { assert ->
        val div = document.createElement("div") as HTMLDivElement
        val form = loginForm(listener)
        processLogin(div, form)
        val message = form.children["message"] as HTMLSpanElement
        assert.ok(message.textContent == "Invalid username or password.", "Error message verified!")
    }

    QUnit.test("process valid login") { assert ->
        val form = loginForm(listener)
        val username = form.children["username"] as HTMLInputElement
        username.value = "admin"
        val password = form.children["password"] as HTMLInputElement
        password.value = "pizza"

        processLogin(div, form)
        assert.ok(div.children["welcome"] != null, "Welcome view found!")
    }

    QUnit.test("process a logout") { assert ->
        processLogout(div)
        assert.ok(div.children["loginform"] != null, "Login form found!")
    }

}