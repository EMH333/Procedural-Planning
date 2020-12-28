use std::collections::HashMap;

//placement priority
type Priority = i16;
//placement count
type GenCount = u16;

type ZoneID = String;
type SpaceID = String;
type FeatureID = String;
type RoomID = String;

pub(crate) struct GenerationConfig {
    pub map_size: u32,
    pub zones: Vec<ZoneConfig>,
    pub spaces: Vec<SpaceConfig>,
    pub rooms: Vec<RoomConfig>,
    pub features: Vec<FeatureConfig>,
}


pub(crate) struct ZoneConfig {
    id: ZoneID,
    min_count: GenCount,
    max_count: GenCount,
    min_area: u32,
    max_area: u32,
    must_be_near: HashMap<ZoneID, Priority>,
    must_not_be_near: HashMap<ZoneID, Priority>,
}


pub(crate) struct SpaceConfig {
    id: SpaceID,
    min_count: GenCount,
    max_count: GenCount,
    generate_in: HashMap<ZoneID, Priority>,
    min_area: u32,
    max_area: u32,
    must_be_near: HashMap<SpaceID, Priority>,
    must_not_be_near: HashMap<SpaceID, Priority>,
}


pub(crate) struct RoomConfig {
    id: RoomID,
    min_count: GenCount,
    max_count: GenCount,
    generate_in: HashMap<SpaceID, Priority>,
    min_area: u32,
    max_area: u32,
    must_be_near: HashMap<RoomID, Priority>,
    must_not_be_near: HashMap<RoomID, Priority>,
}


pub(crate) struct FeatureConfig {
    id: FeatureID,
    min_count: GenCount,
    max_count: GenCount,
    generate_in: HashMap<RoomID, Priority>,
    must_be_near: HashMap<FeatureID, Priority>,
    must_not_be_near: HashMap<FeatureID, Priority>,
}
