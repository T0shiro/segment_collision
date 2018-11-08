package sample

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.math.pow

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
        (1..segmentAmount).forEach { segments.add(Segment(100, 100, width, height, FPS)) }

        window.setInterval({
            context.clearRect(0.0,0.0, canvas.width.toDouble(), canvas.height.toDouble())
            segments.forEach { segment ->
                run {
                    segment.collision(canvas.width, canvas.height)
                    segment.rotate()
                    segment.translate()
                    detectCollisions(segments)
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

    private fun detectCollisions(segments : List<Segment>){
        context.fillStyle = "rgba("+255+","+0+","+0+")"
        segments.forEach { segment ->
            run {
                // segment = AB, segment2 = CD
                segments.forEach { segment2 ->
                    run {
                        // ab = segment.endx - segment.startx, segment.endy - segment.starty
                        // cd = segment2.endx - segment2.startx, segment2.endy - segment2.starty
                        // ac = segment2.startx - segment.startx, segment2.starty - segment.starty
                        // ad = segment2.endx - segment.startx, segment2.endy - segment.starty
                        // ca = segment.startx - segment2.startx, segment.starty - segment2.starty
                        // cb = segment.endx - segment2.startx, segment.endy - segment2.starty

                        var abac = (segment.endx - segment.startx) * (segment2.startx - segment.startx) + (segment.endy - segment.starty) * (segment2.starty - segment.starty)
                        var abad = (segment.endx - segment.startx) * (segment2.endx - segment.startx) + (segment.endy - segment.starty) * (segment2.endy - segment.starty)

                        if (abac >= 0 && abad < 0 || abac < 0 && abad >= 0){
                            var cdca = (segment2.endx - segment2.startx) * (segment.startx - segment2.startx) + (segment2.endy - segment2.starty) * (segment.starty - segment2.starty)
                            var cdcb = (segment2.endx - segment2.startx) * (segment.endx - segment2.startx) + (segment2.endy - segment2.starty) * (segment.endy - segment2.starty)
                            if (cdca >= 0 && cdcb < 0 || cdca < 0 && cdcb >= 0){
                                var x = (abac * segment2.endx - abad * segment2.startx) / (abac - abad)
                                var y = (abac * segment2.endy - abad * segment2.starty) / (abac - abad)
                                context.fillRect(x, y, 5.0, 5.0)
                            }
                        }
                    }
                }
            }
        }
        context.fillStyle = "rgba("+0+","+0+","+0+")"
    }

}