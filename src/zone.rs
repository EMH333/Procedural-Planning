use crate::tile::BaseLevel;
use druid::Color;
use std::hash::{Hash, Hasher};
use std::collections::hash_map::DefaultHasher;
use crate::grid::GridPos;
use crate::util::point_inside_polygon;

pub struct Zone {
    zone_type: Box<str>,
    uuid: u64,
    points: Vec<GridPos>,
}

impl BaseLevel for Zone {
    //quick and easy random colors
    fn get_color(&self) -> Color {
        let mut hasher = DefaultHasher::new();
        self.zone_type.hash(&mut hasher);

        Color::hlc((hasher.finish() % 360) as i16,50,127)
    }

    fn is_point_inside(&self, point: GridPos) -> bool {
        point_inside_polygon(point, &self.points)
    }
}

impl Zone {
    pub fn new(uuid: u64, name: Box<str>) -> Zone {
        Zone {
            zone_type: name,
            uuid,
            points: vec![]
        }
    }
}