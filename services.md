
# Services
Key features of the service layer:

1. DroneService:
    - Manages drone lifecycle (create, enable, disable, retire)
    - Handles station assignment for new drones
    - Implements DTO conversion for API responses

2. DeliveryService:
    - Handles delivery lifecycle (create, schedule, complete)
    - Implements both specific and random drone assignment
    - Manages delivery timing and status updates
    - Validates business rules (drone availability, delivery status)

3. Common patterns:
    - Transactional management with @Transactional
    - Exception handling for business logic errors
    - DTO conversions to isolate entity changes
    - Input validation and error checking

4. Business Rules Implemented:
    - New drones go to least loaded station
    - Deliveries scheduled 30 minutes ahead
    - Only operational drones can be assigned
    - Proper status checking before operations
    - Error handling for invalid operations

