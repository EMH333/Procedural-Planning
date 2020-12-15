use crate::grid::GridPos;

pub(crate) fn point_inside_polygon(point: GridPos, vs: &Vec<GridPos>) -> bool {
    // ray-casting algorithm based on
    // https://wrf.ecse.rpi.edu/Research/Short_Notes/pnpoly.html/pnpoly.html

    let x = point.col as isize;
    let y = point.row as isize;

    // If we never cross any lines we're inside.
    let mut inside = false;

    // Loop through all the edges.
    let mut i = 0;
    while i < vs.len()
    {
        // i is the index of the first vertex, j is the next one.
        // The original code uses a too-clever trick for this.
        let j = (i + 1) % vs.len();

        // The vertices of the edge we are checking.
        let xp0 = vs[i].col as isize;
        let yp0 = vs[i].row as isize;
        let xp1 = vs[j].col as isize;
        let yp1 = vs[j].row as isize;

        // Check whether the edge intersects a line from (-inf,y) to (x,y).

        // First check if the line crosses the horizontal line at y in either direction.
        if (yp0 <= y) && (yp1 > y) || (yp1 <= y) && (yp0 > y)
        {
            // If so, get the point where it crosses that line. This is a simple solution
            // to a linear equation. Note that we can't get a division by zero here -
            // if yp1 == yp0 then the above if be false.
            let cross = (xp1 - xp0) * (y - yp0) / (yp1 - yp0) + xp0;

            // Finally check if it crosses to the left of our test point. You could equally
            // do right and it should give the same result.
            if cross < x {
                inside = !inside;
            }
        }
        i += 1;
    }
    inside
}

#[cfg(test)]
mod tests {
    // Note this useful idiom: importing names from outer (for mod tests) scope.
    use super::*;

    #[test]
    fn test_points_in_polygon() {
        assert_eq!(point_inside_polygon(GridPos { row: 1, col: 1 }, &vec![
            GridPos { col: 0, row: 0 },
            GridPos { col: 0, row: 2 },
            GridPos { col: 2, row: 2 },
            GridPos { col: 2, row: 0 },
        ],
        ), true);
    }
}