
# Services Tests
Key points about the service tests:

1. Test Setup:
    - Uses MockitoExtension for mocking
    - Each test class has its own @BeforeEach setup
    - Test data initialization in setUp() method

2. DroneServiceTest covers:
    - Getting all drones
    - Adding new drone to the least loaded station
    - Error handling for no available stations
    - Drone status changes (enable)

3. DeliveryServiceTest covers:
    - Getting undelivered orders
    - Creating new deliveries
    - Scheduling deliveries (both specific and random drone)
    - Completing deliveries
    - Error handling

4. Testing Patterns:
    - Arrange-Act-Assert pattern
    - Mockito when/verify usage
    - Exception testing
    - DTO conversion verification
