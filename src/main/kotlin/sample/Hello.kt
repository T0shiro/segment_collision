package sample

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Text
import sample.quadtree.Box
import sample.quadtree.QuadTree
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date
import kotlin.math.pow

var results = mutableMapOf<Int, Double>()

fun myApp() {
    SegmentCollisions().run(2.0.pow(8).toInt(), true, false)
}

val canvas = initalizeCanvas()
val fps = initializeText()
var nbBenchExecs = 0

fun initializeText(): Text {
    val fps = document.createTextNode("FPS :")
    document.body!!.appendChild(fps)
    return fps
}

fun initalizeCanvas(): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement

    val context = canvas.getContext("2d") as CanvasRenderingContext2D

    context.canvas.width = 512
    context.canvas.height = 512
    canvas.style.border = "solid"
    document.body!!.appendChild(canvas)
    return canvas
}

class SegmentCollisions {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    val height = canvas.height.toDouble()
    val width = canvas.width.toDouble()

    var quadtree: QuadTree = QuadTree(256, 256, 256)


    fun run(segmentAmount: Int, quad: Boolean, bench: Boolean) {
        nbBenchExecs = 0
        var segments: MutableList<Segment> = mutableListOf<Segment>()
        (1..segmentAmount).forEach {
            val segment = Segment(width, height)
            segments.add(segment)
        }
        updateDisplay(segments, Date.now(), 0, quad, bench)
    }

    fun updateDisplay(segments: List<Segment>, time: Double, frames: Int, quad: Boolean, bench: Boolean) {
        val times = mutableListOf<Double>()
        context.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
        if (quad) {
            quadtree = QuadTree(256, 256, 256)
        }
        segments.forEach { segment ->
            run {
                segment.collision(canvas.width, canvas.height)
                segment.rotate()
                segment.translate()
                if (quad) quadtree.insert(segment)
            }
        }
        draw(segments)
        val t = Date.now()
        if (quad) console.log(quadtree.queryRange(Box(256, 256, 512), context))
        else detectCollisions(segments)
        var t2 = Date.now() - t
        times.add(t2)
        if (bench) {
            nbBenchExecs++
            if (nbBenchExecs == 100) {
                results[segments.size] = times.sum() / times.size
                console.log(segments.size, times.sum() / times.size)
                return
            }
        }
        val now = Date.now()
        if (now > time + 1000) {
            window.requestAnimationFrame { updateDisplay(segments, now, 0, quad, bench) }
            fps.textContent = "FPS : $frames"
        } else {
            window.requestAnimationFrame { updateDisplay(segments, time, frames + 1, quad, bench) }
        }
    }

    private fun draw(segments: List<Segment>) {
        context.beginPath()
        segments.forEach { segment ->
            run {
                var x = segment.x - kotlin.math.cos(segment.angle) * (segment.length / 2)
                var y = segment.y - kotlin.math.sin(segment.angle) * (segment.length / 2)
                var x2 = segment.x + kotlin.math.cos(segment.angle) * (segment.length / 2)
                var y2 = segment.y + kotlin.math.sin(segment.angle) * (segment.length / 2)
                context.moveTo(x, y)
                context.lineTo(x2, y2)
            }
        }
        context.stroke()
    }

    private fun detectCollisions(segments: List<Segment>) {
        context.fillStyle = "rgba(" + 255 + "," + 0 + "," + 0 + ")"
        for (i in 0 until segments.size) {
            var abx = segments[i].endx - segments[i].startx
            var aby = segments[i].endy - segments[i].starty
            for (j in i + 1 until segments.size) {
                var alpha = abx * (segments[j].starty - segments[i].starty) - aby * (segments[j].startx - segments[i].startx)
                var beta = abx * (segments[j].endy - segments[i].starty) - aby * (segments[j].endx - segments[i].startx)
                if (alpha < 0 != beta < 0) {
                    var cdx = segments[j].startx - segments[j].endx
                    var cdy = segments[j].starty - segments[j].endy
                    alpha = cdx * (segments[j].starty - segments[i].starty) - cdy * (segments[j].startx - segments[i].startx)
                    beta = cdx * (segments[j].starty - segments[i].endy) - cdy * (segments[j].startx - segments[i].endx)
                    if (alpha < 0 != beta < 0) {
                        var x = (alpha * segments[i].endx - beta * segments[i].startx) / (alpha - beta)
                        var y = (alpha * segments[i].endy - beta * segments[i].starty) / (alpha - beta)
                        context.fillRect(x, y, 3.0, 3.0)
                    }
                }
            }
        }
        context.fillStyle = "rgba(" + 0 + "," + 0 + "," + 0 + ")"
    }

}