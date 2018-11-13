package sample

import kotlin.math.abs

class Segment {
    var SPEED_DIVIDER = 60
    var ROTATION_DIVIDER = 60
    var x = 0.0
    var y = 0.0
    var startx = 0.0
    var starty = 0.0
    var endx = 0.0
    var endy = 0.0
    var vx = 0.0
    var vy = 0.0
    var angle = (0 until 2 * kotlin.math.PI.toInt()).shuffled()[0].toDouble()
    var rotationSpeed = kotlin.math.PI / 2
    var length = 80

    constructor(vx: Double, vy: Double, maxX : Double, maxY : Double) {
        this.x = (0 until maxX.toInt()).shuffled()[0].toDouble()
        this.y = (0 until maxY.toInt()).shuffled()[0].toDouble()
        updateExtremities()
        this.vx = vx/SPEED_DIVIDER
        this.vy /= vy/SPEED_DIVIDER
        this.rotationSpeed /= ROTATION_DIVIDER
    }

    fun updateExtremities() {
        val prevs = arrayOf(startx, starty, endx, endy)
        this.startx = this.x - kotlin.math.cos(this.angle) * (this.length / 2)
        this.starty = this.y - kotlin.math.sin(this.angle) * (this.length / 2)
        this.endx = this.x + kotlin.math.cos(this.angle) * (this.length / 2)
        this.endy = this.y + kotlin.math.sin(this.angle) * (this.length / 2)
        val afters = arrayOf(startx, starty, endx, endy)
        checkStuck(startx)
    }

    private fun checkStuck(value: Double) {

    }

    fun rotate() {
        this.angle += rotationSpeed
    }

    fun translate() {
        this.x += this.vx
        this.y += this.vy
        updateExtremities()
    }

    fun collision(width: Int, height: Int) {
        var collided = false
        if (arrayOf(startx, endx).any { d -> d >= width }) {
            this.vx = -abs(vx)
            collided = true
        }
        if (arrayOf(startx, endx).any { d -> d <= 0 }) {
            this.vx = abs(vx)
            collided = true
        }
        if (arrayOf(starty, endy).any { d -> d >= height }) {
            this.vy = -abs(vy)
            collided = true
        }
        if (arrayOf(starty, endy).any { d -> d <= 0 }) {
            this.vy = abs(vy)
            collided = true
        }
        if (collided) {
            this.rotationSpeed = -0.5 * rotationSpeed - 3 * (vy * kotlin.math.cos(angle) - vx * kotlin.math.sin(angle)) / (2 * length)
        }
    }

    fun elasticCollision(width: Int, height: Int) {
        val cond = arrayOf(startx, endx).any { d -> d <= 0 || d >= width } || arrayOf(starty, endy).any { d -> d <= 0 || d >= height }
        if (cond) {
            console.log("COLLISION")
            console.log(startx, endx, starty, endy)
            console.log(vx, vy)
            var oldRotationSpeed = rotationSpeed
            this.rotationSpeed = -0.5 * oldRotationSpeed - 3 * (vy * kotlin.math.cos(angle) - vx * kotlin.math.sin(angle)) / (2 * length)
            this.vx = -vx + (length / 2) * kotlin.math.sin(angle) * (oldRotationSpeed + rotationSpeed)
            this.vy = -vy - (length / 2) * kotlin.math.cos(angle) * (oldRotationSpeed + rotationSpeed)
            (0 until 2).forEach {
                rotate()
                translate()
            }
        }
    }
}