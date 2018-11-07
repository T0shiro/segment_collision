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
    val FPS = 40
    val segmentAmount = 100


    fun run() {
        var segments : MutableList<Segment> = mutableListOf<Segment>()
        (1..segmentAmount).forEach { segments.add(Segment(100.0, 100.0, width, height, FPS)) }

        window.setInterval({
            context.clearRect(0.0,0.0, canvas.width.toDouble(), canvas.height.toDouble())
            segments.forEach { segment ->
                run {
                    segment.collision(canvas.width, canvas.height)
                    segment.rotate()
                    segment.translate()
                }
            }
            draw(segments)
        }, 1000/FPS)
    }

    private fun draw(segments : List<Segment>) {
        context.beginPath()
        segments.forEach { segment ->
            run {
                var x = segment.x - kotlin.math.cos(segment.angle)*(segment.length/2)
                var y = segment.y - kotlin.math.sin(segment.angle)*(segment.length/2)
                var x2 = segment.x + kotlin.math.cos(segment.angle)*(segment.length/2)
                var y2 = segment.y + kotlin.math.sin(segment.angle)*(segment.length/2)
                context.moveTo(x, y)
                context.lineTo(x2, y2)
            }
        }
        context.stroke()
    }

}