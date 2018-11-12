package sample

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import sample.quadtree.Box
import sample.quadtree.QuadTree
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.clear
import kotlin.math.pow

fun myApp() {
    FancyLines().run()
}

val canvas = initalizeCanvas()
fun initalizeCanvas(): HTMLCanvasElement {
    val canvas = document.createElement("canvas") as HTMLCanvasElement

    val context = canvas.getContext("2d") as CanvasRenderingContext2D

    context.canvas.width = 512
    context.canvas.height = 512
    document.body!!.appendChild(canvas)
    return canvas
}

class FancyLines {
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    val height = canvas.height.toDouble()
    val width = canvas.width.toDouble()
    val FPS = 60
    val segmentAmount = 400

    val quadtree : QuadTree = QuadTree(256, 256, 256)


    fun run() {
        var segments : MutableList<Segment> = mutableListOf<Segment>()
        (1..segmentAmount).forEach {
            val segment = Segment(100.0, 100.0, width, height, FPS)
            segments.add(segment)
            quadtree.insert(segment)
        }

        window.setInterval({
            context.clearRect(0.0,0.0, canvas.width.toDouble(), canvas.height.toDouble())
            segments.forEach { segment ->
                run {
                    segment.collision(canvas.width, canvas.height)
                    segment.rotate()
                    segment.translate()
                }
            }
            detectCollisions(quadtree.queryRange(Box(256, 256, 256)))
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

    private fun detectCollisions(segments : List<Segment>){
        context.fillStyle = "rgba("+255+","+0+","+0+")"
        for (i in 0 until segments.size) {
            var abx = segments[i].endx - segments[i].startx
            var aby = segments[i].endy - segments[i].starty
            for (j in i+1 until segments.size) {
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
        context.fillStyle = "rgba("+0+","+0+","+0+")"
    }

}