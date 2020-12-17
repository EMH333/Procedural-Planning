use druid::Color;
use crate::zone::Zone;
use crate::grid::GridPos;
use std::cell::RefCell;
use crate::space::Space;
use crate::room::Room;
use crate::feature::Feature;

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
    pub(crate) zone: RefCell<Zone>,
    pub(crate) space: RefCell<Space>,
    pub(crate) room: RefCell<Room>,
    pub(crate) base_feature: RefCell<Feature>
}
