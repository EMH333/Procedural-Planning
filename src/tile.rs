use druid::Color;
use crate::zone::Zone;
use crate::grid::GridPos;
use std::cell::RefCell;
use crate::space::Space;
use crate::room::Room;
use crate::feature::Feature;
use std::sync::{Mutex, Arc};

/*
    The base for zone, space, room and feature levels
    has basic features like rendering in the given space,
    and more to be added later
 */
pub(crate) trait BaseLevel {
    fn get_color(&self) -> Color;
    fn is_point_inside(&self, point: GridPos) -> bool;
}

pub(crate) enum Direction {
    None,
    North,
    South,
    East,
    West
}

/*
    A physical location on the map
 */
pub(crate) struct Tile {
    pub(crate) zone: Arc<Mutex<Zone>>,
    pub(crate) space: Arc<Mutex<Space>>,
    pub(crate) room: Arc<Mutex<Room>>,
    pub(crate) base_feature: Arc<Mutex<Feature>>
}
