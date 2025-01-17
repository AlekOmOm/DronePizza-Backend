
Key points about the controllers:

1. DroneController (/drones):
    - GET / - List all drones
    - POST /add - Create new drone
    - POST /enable - Enable drone
      - `/drones/enable?serialNumber=123`
    - POST /disable - Disable drone
      - `/drones/disable?serialNumber=123`
    - POST /retire - Retire drone
      - `/drones/retire?serialNumber=123`
   note:
    - JSON body response of /drones, doesn't have id, but instead SerialNumber (UUID)
   
2. DeliveryController (/deliveries):
    - GET / - List undelivered orders
    - POST /add?pizzaId=1 - Create new delivery
    - GET /queue - List unassigned deliveries
    - POST /schedule - Assign drone to delivery
      - `/deliveries/schedule?deliveryId=1&droneSerialNumber=123`
    - POST /finish - Mark delivery as completed
      - '/deliveries/finish?deliveryId=1'

3. DTOs:
    - Created to separate API representation from entities
    - Include only necessary fields for the API
    - Help prevent circular references

4. Exception handling:
    - Custom exceptions for different error cases
    - Appropriate HTTP status codes
    - Clear error messages
