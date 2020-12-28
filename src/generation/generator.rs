use crate::grid::{Grid, GridPos};
use crate::generation::config::GenerationConfig;
use std::sync::{Arc, Mutex};
use crate::tile::Tile;
use std::ops::Index;
use std::cell::RefCell;
use crate::room::Room;
use crate::zone::Zone;
use crate::space::Space;
use crate::feature::Feature;
use crate::util::UUIDCounter;
use num::integer::Roots;

fn generate_map(config: GenerationConfig) -> Grid {
    let mut counter: UUIDCounter = UUIDCounter::new();
    let base_zone = Arc::new(Mutex::new(Zone::new(counter.next_uuid())));
    let base_space = Arc::new(Mutex::new(Space::new(counter.next_uuid())));
    let base_room = Arc::new(Mutex::new(Room::new(counter.next_uuid())));

    let mut map: Vec<Tile> = Vec::with_capacity((config.map_size * config.map_size) as usize);
    for x in 0..config.map_size {
        for y in 0..config.map_size {
            map.set_tile(Tile {
                zone: base_zone.clone(),
                space: base_space.clone(),
                room: base_room.clone(),
                base_feature: Arc::new(Mutex::new(Feature::new(counter.next_uuid(), GridPos { row: y as usize, col: x as usize }))),
            }, x, y)
        }
    }
    Grid { storage: Arc::new(vec![]) }
}

trait TileStore {
    fn get_tile(&self, x: u32, y: u32) -> &Tile;
    fn set_tile(&mut self, tile: Tile, x: u32, y: u32);
}

impl TileStore for Vec<Tile> {
    fn get_tile(&self, x: u32, y: u32) -> &Tile {
        let size = self.capacity().sqrt();
        self.index((y as usize * size) + x as usize)
    }

    fn set_tile(&mut self, tile: Tile, x: u32, y: u32) {
        let size = self.capacity().sqrt();
        self[(y as usize * size) + x as usize] = tile;
    }
}

