# Procedural Planner

This is a work in progress procedural planning tool. The MVP will be able to procedurally generate areas such as a neighborhood, campus, estate or farm based on rules for placement of certain objects. 

It is currently designed with four levels of abstraction in mind:

- Zones

    Broad areas, in the 100's of meters, that contain multiple buildings and have a certain set of rules associated with them. An example might be a city zoning restriction.
  
- Space

    What could traditionally be considered a building such as a house or shed as well as outdoor spaces such as lawns or forests. A space might have several related purposes or be general use. A space could encompass things both inside and outside. Probably in the 10's of meters.
  
- Room

    A room inside a building or some subset of a space. Generally rooms will have a specific purpose and be sized to accommodate that use. Examples include bedrooms, ballrooms, storage, utilities, garden bed, grazing area, etc.
  
- Feature

    This is a small feature of a room like a sink, door, window, wall or fence. These don't have to be in a room (in the case of a fence or wall), but can have a direction associated with them and can have several sub-features.
  
The configurations will be read from some sort of typed files (JSON could work in a pinch) and based on optimization factors such as total space, cost, distance between objects, etc. and rules such as number of windows, minimum space size, etc. will be used to generate the optimal layout and plan. 

This plan will then be rendered in 2D and displayed to the user with options to save to a file and/or modify the constrains to regenerate. 