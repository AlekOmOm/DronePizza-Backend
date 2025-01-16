home: [readme](readme.md)

# Controller Tests

- [test coverage - controller](test-coverage---controller)
- flow of controller tests:
  - Test -> Controller -> Service -> Exception -> Controller -> Response

Key points about the controller tests:

1. MockBean used for:
   - mocking dependencies in spring mvc tests (get and post requests)
   - integration with spring's dependency injection
   - supporting controller layer testing

2. Test Setup:
    - Uses @WebMvcTest for focused controller testing
    - MockMvc for simulating HTTP requests
    - ObjectMapper for JSON serialization
    - MockBean for service layer mocking

3. DroneControllerTest covers:
    - GET /drones - List all drones
    - POST /drones/add - Create new drone
    - POST /drones/enable - Enable drone
    - Error handling for no stations

4. DeliveryControllerTest covers:
    - GET /deliveries - List undelivered orders
    - POST /deliveries/add - Create delivery
    - POST /deliveries/schedule - With specific and random drone
    - POST /deliveries/finish - Complete delivery
    - Error handling

5. Testing Patterns:
    - HTTP status verification
    - JSON response validation
    - Request parameter handling
    - Error scenarios
