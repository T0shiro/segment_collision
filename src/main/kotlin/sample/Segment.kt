package sample


class Segment {
    var x = 0.0
    var y = 0.0
    var vx = 0
    var vy = 0
    var angle = 0.0
    var rotationSpeed = 0
    var length = 80

    private fun randomAngle(): Double = (0 until 2*kotlin.math.PI.toInt()).shuffled()[0].toDouble()

    constructor(vx: Int, vy: Int, rotationSpeed: Int, maxX : Double, maxY : Double) {
        fun nextX(): Double = (0 until maxX.toInt()).shuffled()[0].toDouble()
        fun nextY(): Double = (0 until maxY.toInt()).shuffled()[0].toDouble()
        this.x = nextX()
        this.y = nextY()
        this.vx = vx
        this.vy = vy
        this.angle = randomAngle()
        this.rotationSpeed = rotationSpeed
    }

    fun rotate(){
        this.angle = randomAngle()
    }
}