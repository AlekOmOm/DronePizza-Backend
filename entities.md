
# Entities with JPA

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
