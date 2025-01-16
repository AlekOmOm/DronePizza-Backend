
## Entities with JPA

1. Relations:
    - Used @ManyToOne for child side (Drone → Station, Delivery → Drone, Delivery → Pizza)
    - Used @OneToMany for parent side with mappedBy
    - Made drone optional in Delivery (nullable = true by default)
    - Made station required in Drone (nullable = false)

2. Important attributes:
    - Drone: UUID for serialNumber (not primary key as specified)
    - Station: latitude/longitude as Double for GPS coordinates
    - Pizza: priceInDKK as Integer for whole numbers
    - Delivery: LocalDateTime for timestamps

3. Fetch strategies:
    - EAGER fetch for Drone's station (likely needed often)
    - LAZY fetch for Station's drones (might be many)
    - Default EAGER for Pizza and Delivery relations

4. Collections:
    - Initialized empty ArrayList for all @OneToMany collections
    - This prevents null pointer exceptions


### Relations and Fetch Strategies

1. Parent-Child relations:
   - Station is parent, Drone is child
   - Delivery is parent, Drone and Pizza are children
   
   - Drone is optional in Delivery
   - Station is required in Drone

2. UML relation types:
    - Station -**_aggregation_**→ Drone
      - station is responsible for drones, but can exist without
      - Drone entity on the other hand *MUST* have a station
        - `@JoinColumn(name = "station_id", nullable = false)`
    - Delivery -composition→ Drone
    - Delivery -composition→ Pizza

3. Bi-Directional relations
    - Station has OneToMany Drones (mappedBy)
      - Station -one-many→ Drone
    - Drone has ManyToOne Station
      - Drone -many-one→ Station
   similar with:
    - Delivery → Drone
    - Delivery → Pizza




explanation and consequence with Station and Drone as examples:
    

