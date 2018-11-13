package sample

import kotlin.math.abs

class Segment {
    var x = 0.0
    var y = 0.0
    var startx = 0.0
    var starty = 0.0
    var endx = 0.0
    var endy = 0.0
    var translationX = 1.0
    var translationY = 1.0
    var angle = (0 until 2 * kotlin.math.PI.toInt()).shuffled()[0].toDouble()
    var rotationSpeed = kotlin.math.PI / 120
    var length = 80

    constructor(maxX : Double, maxY : Double) {
        this.x = (0 until maxX.toInt()).shuffled()[0].toDouble()
        this.y = (0 until maxY.toInt()).shuffled()[0].toDouble()
        updateExtremities()
    }

    fun updateExtremities() {
        this.startx = this.x - kotlin.math.cos(this.angle) * (this.length / 2)
        this.starty = this.y - kotlin.math.sin(this.angle) * (this.length / 2)
        this.endx = this.x + kotlin.math.cos(this.angle) * (this.length / 2)
        this.endy = this.y + kotlin.math.sin(this.angle) * (this.length / 2)
    }

    fun rotate() {
        this.angle += rotationSpeed
    }

    fun translate() {
        this.x += this.translationX
        this.y += this.translationY
        updateExtremities()
    }

    fun collision(width: Int, height: Int) {
        var rand = 1/(2 ..10).shuffled()[0].toDouble()
        var collided = false
        if (arrayOf(startx, endx).any { d -> d >= width }) {
            this.translationX = -abs(translationX)
            this.translationY += rand
            collided = true
        }
        if (arrayOf(startx, endx).any { d -> d <= 0 }) {
            this.translationX = abs(translationX)
            this.translationY += rand
            collided = true
        }
        if (arrayOf(starty, endy).any { d -> d >= height }) {
            this.translationY = -abs(translationY)
            this.translationX += rand
            collided = true
        }
        if (arrayOf(starty, endy).any { d -> d <= 0 }) {
            this.translationY = abs(translationY)
            this.translationX += rand
            collided = true
        }
        if (collided) {
            this.rotationSpeed = -0.5 * rotationSpeed - 3 * (translationY * kotlin.math.cos(angle) - translationX * kotlin.math.sin(angle)) / (2 * length)
        }
    }

    fun elasticCollision(width: Int, height: Int) {
        val cond = arrayOf(startx, endx).any { d -> d <= 0 || d >= width } || arrayOf(starty, endy).any { d -> d <= 0 || d >= height }
        if (cond) {
            console.log("COLLISION")
            console.log(startx, endx, starty, endy)
            console.log(translationX, translationY)
            var oldRotationSpeed = rotationSpeed
            this.rotationSpeed = -0.5 * oldRotationSpeed - 3 * (translationY * kotlin.math.cos(angle) - translationX * kotlin.math.sin(angle)) / (2 * length)
            this.translationX = -translationX + (length / 2) * kotlin.math.sin(angle) * (oldRotationSpeed + rotationSpeed)
            this.translationY = -translationY - (length / 2) * kotlin.math.cos(angle) * (oldRotationSpeed + rotationSpeed)
            rotate()
            translate()
        }
    }
}