# Segment collisions
This project aims to show collisions of segments using Kotlin.
 
## Members
- GARDAIRE Loïc
- MORTARA Johann


## How to run it 
1. ``./gradlew clean build``
1. Open ``./web/index.html`` file in your browser

## Methods used to detect collisions

### Naive method : Double for loops
Each frame, we loop on each segment and check if another segment collides with it.

### Optimisation : Quadtree
Each frame, we subdivide the canvas in 4 parts as many times as necessary to have a minimum of segments in each part.
