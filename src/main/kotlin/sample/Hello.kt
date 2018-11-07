package sample

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.browser.window

fun myApp() {
    FancyLines().run()
}

val canvas = initalizeCanvas()
fun initalizeCanvas(): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    context.canvas.width = window.innerWidth.toInt();
    context.canvas.height = window.innerHeight.toInt();
    document.body!!.appendChild(canvas)
    return canvas
}

class FancyLines {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    val height = canvas.height.toDouble()
    val width = canvas.width.toDouble()

    fun run() {
        var segments : MutableList<Segment> = mutableListOf<Segment>()
        (1..10).forEach { segments.add(Segment(0, 0, 0, 0, width, height)) }
        draw(segments)
    }

    private fun draw(segments : List<Segment>) {
        context.beginPath()
        segments.forEach { segment ->
            run {
                context.moveTo(segment.x, segment.y)
                context.lineTo(segment.x + segment.length, segment.y)
            }
        }
        context.stroke()
    }

}