import tiles.GridPos

fun pointInsidePolygon(point: GridPos, vs: Array<GridPos>): Boolean {
    // ray-casting algorithm based on
    // https://wrf.ecse.rpi.edu/Research/Short_Notes/pnpoly.html/pnpoly.html

    val x = point.col
    val y = point.row

    // If we never cross any lines we're inside.
    var inside = false;

    // Loop through all the edges.
    var i = 0;
    while (i < vs.size) {
        // i is the index of the first vertex, j is the next one.
        // The original code uses a too-clever trick for this.
        val j = (i + 1) % vs.size;

        // The vertices of the edge we are checking.
        val xp0 = vs[i].col
        val yp0 = vs[i].row
        val xp1 = vs[j].col
        val yp1 = vs[j].row

        // Check whether the edge intersects a line from (-inf,y) to (x,y).

        // First check if the line crosses the horizontal line at y in either direction.
        if ((yp0 <= y) && (yp1 > y) || (yp1 <= y) && (yp0 > y)) {
            // If so, get the point where it crosses that line. This is a simple solution
            // to a linear equation. Note that we can't get a division by zero here -
            // if yp1 == yp0 then the above if be false.
            val cross = (xp1 - xp0) * (y - yp0) / (yp1 - yp0) + xp0;

            // Finally check if it crosses to the left of our test point. You could equally
            // do right and it should give the same result.
            if (cross < x) {
                inside = !inside;
            }
        }
        i += 1;
    }
    return inside
}