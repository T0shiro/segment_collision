package sample.quadtree

import sample.Segment

class Box {
    var xCenter = 0
    var yCenter = 0
    var halfDimension = 0

    constructor(xCenter: Int, yCenter: Int, halfDimension: Int) {
        this.xCenter = xCenter
        this.yCenter = yCenter
        this.halfDimension = halfDimension
    }


    fun containsSegment(s: Segment): Boolean {

        return (s.x >= xCenter - halfDimension
                && s.x <= xCenter + halfDimension
                && s.y >= yCenter - halfDimension
                && s.y <= yCenter + halfDimension)
                || (s.startx >= xCenter - halfDimension
                && s.startx <= xCenter + halfDimension
                && s.starty >= yCenter - halfDimension
                && s.starty <= yCenter + halfDimension)
                || (s.endx >= xCenter - halfDimension
                && s.endx <= xCenter + halfDimension
                && s.endy >= yCenter - halfDimension
                && s.endy <= yCenter + halfDimension)
    }

    fun intersectsWithOtherBox(other: Box): Boolean {
        return other.xCenter - other.halfDimension < xCenter + halfDimension
                && other.xCenter + other.halfDimension > xCenter - halfDimension
                && other.yCenter - other.halfDimension < yCenter + halfDimension
                && other.yCenter + other.halfDimension > yCenter - halfDimension
    }
}