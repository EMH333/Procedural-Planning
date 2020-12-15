use crate::{GRID_SIZE, POOL_SIZE};
use std::sync::Arc;
use druid::{
    Data,
};

#[allow(clippy::clippy::rc_buffer)]
#[derive(Clone, Data)]
pub(crate) struct Grid {
    pub(crate) storage: Arc<Vec<bool>>,
}

#[derive(Clone, Copy, PartialEq, Data)]
pub(crate) struct GridPos {
    pub(crate) row: usize,
    pub(crate) col: usize,
}


impl GridPos {
    pub fn above(self) -> Option<GridPos> {
        if self.row == 0 {
            None
        } else {
            Some(GridPos {
                row: self.row - 1,
                col: self.col,
            })
        }
    }
    pub fn below(self) -> Option<GridPos> {
        if self.row == GRID_SIZE - 1 {
            None
        } else {
            Some(GridPos {
                row: self.row + 1,
                col: self.col,
            })
        }
    }
    pub fn left(self) -> Option<GridPos> {
        if self.col == 0 {
            None
        } else {
            Some(GridPos {
                row: self.row,
                col: self.col - 1,
            })
        }
    }
    pub fn right(self) -> Option<GridPos> {
        if self.col == GRID_SIZE - 1 {
            None
        } else {
            Some(GridPos {
                row: self.row,
                col: self.col + 1,
            })
        }
    }
    #[allow(dead_code)]
    pub fn above_left(self) -> Option<GridPos> {
        self.above().and_then(|pos| pos.left())
    }
    pub fn above_right(self) -> Option<GridPos> {
        self.above().and_then(|pos| pos.right())
    }
    #[allow(dead_code)]
    pub fn below_left(self) -> Option<GridPos> {
        self.below().and_then(|pos| pos.left())
    }
    pub fn below_right(self) -> Option<GridPos> {
        self.below().and_then(|pos| pos.right())
    }
}


impl Grid {
    pub fn new() -> Grid {
        Grid {
            storage: Arc::new(vec![false; POOL_SIZE]),
        }
    }
    pub fn evolve(&mut self) {
        let mut indices_to_mutate: Vec<GridPos> = vec![];
        for row in 0..GRID_SIZE {
            for col in 0..GRID_SIZE {
                let pos = GridPos { row, col };
                let n_lives_around = self.n_neighbors(pos);
                let life = self[pos];
                // death by loneliness or overcrowding
                if life && (n_lives_around < 2 || n_lives_around > 3) {
                    indices_to_mutate.push(pos);
                    continue;
                }
                // resurrection by life support
                if !life && n_lives_around == 3 {
                    indices_to_mutate.push(pos);
                }
            }
        }
        for pos_mut in indices_to_mutate {
            self[pos_mut] = !self[pos_mut];
        }
    }

    pub fn neighbors(pos: GridPos) -> [Option<GridPos>; 8] {
        let above = pos.above();
        let below = pos.below();
        let left = pos.left();
        let right = pos.right();
        let above_left = above.and_then(|pos| pos.left());
        let above_right = above.and_then(|pos| pos.right());
        let below_left = below.and_then(|pos| pos.left());
        let below_right = below.and_then(|pos| pos.right());
        [
            above,
            below,
            left,
            right,
            above_left,
            above_right,
            below_left,
            below_right,
        ]
    }

    pub fn n_neighbors(&self, pos: GridPos) -> usize {
        Grid::neighbors(pos)
            .iter()
            .filter(|x| x.is_some() && self[x.unwrap()])
            .count()
    }

    pub fn set_alive(&mut self, positions: &[GridPos]) {
        for pos in positions {
            self[*pos] = true;
        }
    }

    #[allow(dead_code)]
    pub fn set_dead(&mut self, positions: &[GridPos]) {
        for pos in positions {
            self[*pos] = false;
        }
    }

    pub fn clear(&mut self) {
        for row in 0..GRID_SIZE {
            for col in 0..GRID_SIZE {
                self[GridPos { row, col }] = false;
            }
        }
    }
}
