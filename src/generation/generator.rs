use crate::grid::GridPos;
use crate::generation::config::GenerationConfig;
use std::sync::{Arc, Mutex};
use crate::tile::Tile;
use std::ops::{Index, IndexMut, DerefMut, Deref};
use crate::room::Room;
use crate::zone::Zone;
use crate::space::Space;
use crate::feature::Feature;
use crate::util::UUIDCounter;
use num::integer::Roots;
use std::borrow::{Borrow, BorrowMut};

pub fn generate_map(config: GenerationConfig) -> Vec<Tile> {
    let map_size = config.map_size;
    let mut counter: UUIDCounter = UUIDCounter::new();

    let mut map = base_map(map_size, &mut counter);

    //super simple zone generation
    let mut zones = config.zones.iter();
    for x in 0..map_size {
        if config.zones.len() > x as usize {
            let zone = zones.next().unwrap().clone();
            let new_zone = Arc::new(Mutex::new(
                Zone::new(counter.next_uuid(), Box::from(zone.id.clone()))
            ));

            for y in 0..map_size {
                let mut tile = map.get_tile(x, y).borrow_mut();
                //let mut t_zone = tile.zone.lock().expect("Couldn't lock zone mutex");
                //let mut idk = Arc::try_unwrap(tile.zone).unwrap_or(Mutex::new()).into_inner().unwrap();
                drop(*tile.zone);
                map.get_tile(x, y).zone = new_zone.clone();
                //std::mem::drop(tile.zone)
            }
        }
    }

    map
}

fn base_map(map_size: u32, counter: &mut UUIDCounter) -> Vec<Tile> {
    let base_zone = Arc::new(Mutex::new(Zone::new(counter.next_uuid(), Box::from("Base"))));
    let base_space = Arc::new(Mutex::new(Space::new(counter.next_uuid(), Box::from("Base"))));
    let base_room = Arc::new(Mutex::new(Room::new(counter.next_uuid(), Box::from("Base"))));

    let mut map: Vec<Tile> = Vec::with_capacity((map_size * map_size) as usize);
    for x in 0..map_size {
        for y in 0..map_size {
            map.set_tile(Tile {
                zone: base_zone.clone(),
                space: base_space.clone(),
                room: base_room.clone(),
                base_feature: Arc::new(Mutex::new(Feature::new(
                    counter.next_uuid(),
                    GridPos { row: y as usize, col: x as usize },
                    Box::from("Base")))),
            }, x, y)
        }
    }
    map
}

trait TileStore {
    fn get_tile(&mut self, x: u32, y: u32) -> &mut Tile;
    fn set_tile(&mut self, tile: Tile, x: u32, y: u32);
}

impl TileStore for Vec<Tile> {
    fn get_tile(&mut self, x: u32, y: u32) -> &mut Tile {
        let size = self.capacity().sqrt();
        self.index_mut((y as usize * size) + x as usize)
    }

    fn set_tile(&mut self, tile: Tile, x: u32, y: u32) {
        let size = self.capacity().sqrt();
        self[(y as usize * size) + x as usize] = tile;
    }
}

