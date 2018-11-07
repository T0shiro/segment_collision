package sample


class Segment {
    var x = 0.0
    var y = 0.0
    var vx = 30
    var vy = 30
    var angle = (0 until 2*kotlin.math.PI.toInt()).shuffled()[0].toDouble()
    var rotationSpeed = kotlin.math.PI/2
    var length = 80

    constructor(vx: Int, vy: Int, maxX : Double, maxY : Double) {
        this.x = (0 until maxX.toInt()).shuffled()[0].toDouble()
        this.y = (0 until maxY.toInt()).shuffled()[0].toDouble()
        this.vx = vx
        this.vy = vy
    }

    fun rotate(){
        this.angle += rotationSpeed/2
    }

    fun translate(){
        this.x += vx
        this.y += vy
    }
}