
The key learnings from debugging:

1. JPA Entity Relationships
   - Foreign keys always go on the "Many" side of @OneToMany relationships
   - Station doesn't need foreign keys to Drones - it finds its drones through the station_id in the Drone table
   - The `mappedBy` attribute tells JPA which field manages the relationship

2. Java Collections and Object Equality
   - `List.contains()` relies on either object reference equality or the `equals()` method
   - Without proper `equals()` implementation, collections compare object references
   - For JPA entities, we should implement `equals()` using:
       - Database ID if both entities are persisted
       - Business key (like serialNumber) as fallback
       - Never use transient fields in equals()

3. Bidirectional Relationship Management
   - Need to manage both sides of the relationship (Station.drones and Drone.station)
   - Best practice is to centralize relationship management in one place (like Station.addDrone())
   - Even with EAGER loading, sometimes need to refresh entities after save operations

4. Testing JPA Relationships
   - Always test both sides of bidirectional relationships
   - Verify both the parent-to-child and child-to-parent navigation
   - Consider implementing toString() for better debugging output
   - Be careful with circular references in toString() methods

