use crate::tile::{BaseLevel, Direction};
use druid::Color;
use std::hash::{Hash, Hasher};
use std::collections::hash_map::DefaultHasher;
use crate::grid::GridPos;

pub(crate) struct Feature {
    feature_type: Box<str>,
    uuid: u64,
    facing: Direction,
    sub_features: Vec<Feature>,
    point: GridPos,
}

impl BaseLevel for Feature {
    //quick and easy random colors
    fn get_color(&self) -> Color {
        let mut hasher = DefaultHasher::new();
        self.feature_type.hash(&mut hasher);

        Color::hlc((hasher.finish() % 360) as i16, 50, 127)
    }

    fn is_point_inside(&self, point: GridPos) -> bool {
        self.point.eq(&point) || self.sub_features.iter().any(|x| x.point.eq(&point))
    }
}