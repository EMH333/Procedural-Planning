use druid::Color;
use crate::zone::Zone;
use crate::grid::GridPos;
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
pub struct Tile {
    pub zone: Arc<Mutex<Zone>>,
    pub space: Arc<Mutex<Space>>,
    pub room: Arc<Mutex<Room>>,
    pub base_feature: Arc<Mutex<Feature>>
}
