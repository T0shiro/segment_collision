package sample

import org.w3c.dom.CanvasRenderingContext2D

class Segment {
    var x = 0.0
    var y = 0.0
    var vx = 0
    var vy = 0
    var angle = 0
    var rotationSpeed = 0

    constructor(vx: Int, vy: Int, angle: Int, rotationSpeed: Int, maxX : Double, maxY : Double) {
        fun nextX(): Double = (0 until maxX.toInt()).shuffled()[0].toDouble()
        fun nextY(): Double = (0 until maxY.toInt()).shuffled()[0].toDouble()
        this.x = nextX()
        this.y = nextY()
//        this.x = x
//        this.y = y
        this.vx = vx
        this.vy = vy
        this.angle = angle
        this.rotationSpeed = rotationSpeed
    }

    fun draw(context : CanvasRenderingContext2D, width : Double, height : Double) {
        context.beginPath()
        context.moveTo(this.x, this.y)
        context.lineTo(x + 30, y - 40)
        context.stroke()
    }
}