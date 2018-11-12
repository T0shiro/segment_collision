package sample.quadtree

import sample.Segment

class QuadTree(var xCenter: Int, var yCenter: Int, halfDimmension: Int) {
    var QT_NODE_CAPACITY = 4

    var boundary: Box? = null

    val segments: MutableList<Segment> = mutableListOf()

    var halfDimension = halfDimmension

    var northWest: QuadTree? = null
    var northEast: QuadTree? = null
    var southWest: QuadTree? = null
    var southEast: QuadTree? = null

    init {
        this.boundary = Box(xCenter, yCenter, halfDimension)
    }

    fun insert(segment: Segment): Boolean {
        if (!boundary!!.containsSegment(segment))
            return false

        if (segments.size < QT_NODE_CAPACITY) {
            segments.add(segment)
            return true
        }

        if (northWest == null)
            subdivide()

        if (northWest!!.insert(segment)) return true
        if (northEast!!.insert(segment)) return true
        if (southWest!!.insert(segment)) return true
        if (southEast!!.insert(segment)) return true

        return false
    }

    fun queryRange(range: Box): MutableList<Segment> {
        // Préparer le tableau de résultats
        val segmentsInRange: MutableList<Segment> = mutableListOf()

        // Tout interrompre si la «zone de recherche» n'intersecte pas ce quadrant
        if (!boundary!!.intersectsWithOtherBox(range))
            return segmentsInRange // liste vide

        // Vérifier les objets à ce niveau de quadrant
        for (p in 0..segments.size) {
            if (range.containsSegment(segments[p]))
                segmentsInRange.add(segments[p])
        }

        // Terminer ici, s'il n'y a pas d'enfant
        if (northWest == null)
            return segmentsInRange

        // Sinon, relancer la recherche sur les 4 enfants et ajouter les points trouvés
        segmentsInRange.addAll(northWest!!.queryRange(range))
        segmentsInRange.addAll(northEast!!.queryRange(range))
        segmentsInRange.addAll(southWest!!.queryRange(range))
        segmentsInRange.addAll(southEast!!.queryRange(range))

        return segmentsInRange
    }

    fun subdivide() {
        val half = halfDimension / 2
        northWest = QuadTree(xCenter - half, yCenter + half, half)
        northEast = QuadTree(xCenter + half, yCenter + half, half)
        southWest = QuadTree(xCenter - half, yCenter - half, half)
        southEast = QuadTree(xCenter + half, yCenter - half, half)
    }
}