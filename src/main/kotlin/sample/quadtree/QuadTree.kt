package sample.quadtree

import org.w3c.dom.CanvasRenderingContext2D
import sample.Segment

class QuadTree(var xCenter: Int, var yCenter: Int, halfDimmension: Int) {
    var MAX_NODE_CAPACITY = 4

    var boundary: Box? = null

    var segments: MutableList<Segment> = mutableListOf()

    var halfDimension = halfDimmension

    var northWest: QuadTree? = null
    var northEast: QuadTree? = null
    var southWest: QuadTree? = null
    var southEast: QuadTree? = null

    init {
        this.boundary = Box(xCenter, yCenter, halfDimension)
    }

    fun insert(segment: Segment): Boolean {
        if (!boundary!!.containsSegment(segment)){
            return false
        }

        if (segments.size < MAX_NODE_CAPACITY) {
            segments.add(segment)
            return true
        }

        if (northWest == null) {
            subdivide()
            for (segment in segments){
                insert(segment)
            }
        }

        if (northWest!!.insert(segment)) return true
        if (northEast!!.insert(segment)) return true
        if (southWest!!.insert(segment)) return true
        if (southEast!!.insert(segment)) return true

        return false
    }

    fun queryRange(range: Box, context: CanvasRenderingContext2D): Boolean {
        val segmentsInRange: MutableList<Segment> = mutableListOf()

        // Tout interrompre si la «zone de recherche» n'intersecte pas ce quadrant
        if (!boundary!!.intersectsWithOtherBox(range))
            return false // liste vide

        // Vérifier les objets à ce niveau de quadrant
        for (p in 0 until segments.size) {
            if (range.containsSegment(segments[p]))
                segmentsInRange.add(segments[p])
        }

        // Chercher les collisions ici s'il n'y a pas d'enfant
        if (northWest == null) {
            detectCollisions(segmentsInRange, context)
        } else {
            // Sinon, relancer la recherche sur les 4 enfants
            northWest!!.queryRange(range, context)
            northEast!!.queryRange(range, context)
            southWest!!.queryRange(range, context)
            southEast!!.queryRange(range, context)
        }
        return true
    }

    fun subdivide() {
        val half = halfDimension / 2
        northWest = QuadTree(xCenter - half, yCenter + half, half)
        northEast = QuadTree(xCenter + half, yCenter + half, half)
        southWest = QuadTree(xCenter - half, yCenter - half, half)
        southEast = QuadTree(xCenter + half, yCenter - half, half)
    }

    private fun detectCollisions(segments: List<Segment>, context: CanvasRenderingContext2D) {
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

    fun deleteAll(): Boolean {
        this.segments = mutableListOf()

        if (northEast != null) {
            northWest!!.deleteAll()
            northEast!!.deleteAll()
            southEast!!.deleteAll()
            southWest!!.deleteAll()
        }

        return true
    }
}