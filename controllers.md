
Key points about the controllers:

1. DroneController (/drones):
    - GET / - List all drones
    - POST /add - Create new drone
    - POST /enable - Enable drone
    - POST /disable - Disable drone
    - POST /retire - Retire drone

2. DeliveryController (/deliveries):
    - GET / - List undelivered orders
    - POST /add - Create new delivery
    - GET /queue - List unassigned deliveries
    - POST /schedule - Assign drone to delivery
    - POST /finish - Mark delivery as completed

3. DTOs:
    - Created to separate API representation from entities
    - Include only necessary fields for the API
    - Help prevent circular references

4. Exception handling:
    - Custom exceptions for different error cases
    - Appropriate HTTP status codes
    - Clear error messages
