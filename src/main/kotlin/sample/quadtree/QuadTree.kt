package sample.quadtree

import sample.Segment

class QuadTree(
        xCenter : Int,
        yCenter : Int,
        halfDimmension : Int
){
    var QT_NODE_CAPACITY = 4

    var boundary : Box = Box(xCenter, yCenter, halfDimmension)

    val segments : MutableList<Segment> = mutableListOf()

    // Enfants
    var half = halfDimmension/2
    var northWest : QuadTree = QuadTree(xCenter - half, yCenter + half, half)
    var northEast : QuadTree = QuadTree(xCenter + half, yCenter + half, half)
    var southWest : QuadTree = QuadTree(xCenter - half, yCenter - half, half)
    var southEast : QuadTree = QuadTree(xCenter + half, yCenter - half, half)



    fun insert(segment: Segment) : Boolean {
        // Ignorer les objets qui n'appartiennent pas à ce quadtree
        if (!boundary.containsSegment(segment))
            return false  // l'objet ne doit pas être ajouté

        // S'il reste de la place dans ce quadtree, y ajouter l'objet
        if (segments.size < QT_NODE_CAPACITY)
        {
            segments.add(segment)
            return true
        }

        // Sinon, subdiviser le quadtree, puis ajouter le point au nœud qui l'acceptera
        if (northWest == null)
            subdivide()

        if (northWest.insert(segment)) return true
        if (northEast.insert(segment)) return true
        if (southWest.insert(segment)) return true
        if (southEast.insert(segment)) return true

        // Sinon, le point ne peut être inséré, pour une raison inconnue (cela ne devrait jamais arriver)
        return false
    }

    fun queryRange(range : Box) : MutableList<Segment>
    {
        // Préparer le tableau de résultats
        val pointsInRange : MutableList<Segment> = mutableListOf()

        // Tout interrompre si la «zone de recherche» n'intersecte pas ce quadrant
        if (!boundary.intersectsWithOtherBox(range))
            return pointsInRange // liste vide

        // Vérifier les objets à ce niveau de quadrant
        for (p in 0..segments.size) {
            if (range.containsSegment(segments[p]))
                pointsInRange.add(segments[p])
        }

        // Terminer ici, s'il n'y a pas d'enfant
        if (northWest == null)
            return pointsInRange

        // Sinon, relancer la recherche sur les 4 enfants et ajouter les points trouvés
        pointsInRange.addAll(northWest.queryRange(range))
        pointsInRange.addAll(northEast.queryRange(range))
        pointsInRange.addAll(southWest.queryRange(range))
        pointsInRange.addAll(southEast.queryRange(range))

        return pointsInRange
    }

    fun subdivide(){

    }
}