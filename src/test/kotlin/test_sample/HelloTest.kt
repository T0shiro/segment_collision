package test_sample

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSpanElement
import org.w3c.dom.get
import sample.loginForm
import sample.lineBreak
import sample.processLogin
import sample.welcomeView
import kotlin.browser.document

fun myApp() {
    QUnit.test("build a linebreak") { assert ->
        assert.ok(lineBreak().tagName == "BR", "Passed!")
    }

    QUnit.test("build a login form") { assert ->
        val form = loginForm();
        assert.ok(form.tagName == "FORM", "Passed!")

        assert.ok(form.getElementsByTagName("INPUT").length == 2, "Passed!")
        assert.ok(form.getElementsByTagName("BUTTON").length == 1, "Passed!")

        assert.ok(form.children["username"] != null, "Passed!")
        assert.ok(form.children["password"] != null, "Passed!")
    }

    QUnit.test("build a welcome view") { assert ->
        val view = welcomeView()
        assert.ok(view.tagName == "SPAN", "Passed!")
        assert.ok(view.getAttribute("id") == "message", "Passed!")
    }

    QUnit.test("process invalid login") { assert ->
        val div = document.createElement("div") as HTMLDivElement
        val form = loginForm()
        processLogin(div, form)
        val message = form.children["message"] as HTMLSpanElement
        assert.ok(message.textContent == "Invalid username or password.", "Passed!")
    }

    QUnit.test("process valid login") { assert ->
        val div = document.createElement("div") as HTMLDivElement
        val form = loginForm()

        val username = form.children["username"] as HTMLInputElement
        username.value = "admin"
        val password = form.children["password"] as HTMLInputElement
        password.value = "pizza"

        processLogin(div, form)
        val message = div.children["message"] as HTMLSpanElement
        assert.ok(message.textContent == "Welcome!", "Passed!")
    }

}