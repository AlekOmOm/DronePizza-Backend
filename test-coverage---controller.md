home: [readme](readme.md)


# Test Coverage - Controller

## log of test coverage for controller-tests

### Log 1: 
1. missing test scenarios:
   1) verify a delivery cannot be finished without an assigned drone
   2) verify data consistency after delivery operations

2. tests created to resolve this:
   - `finishDelivery_ShouldFail_WhenNoDroneAssigned()` at line 52 in DeliveryControllerTest.java

3. results:
   - [x] added new tests for extended coverage
   - [x] verified a delivery cannot be finished without an assigned drone
   - [x] verified data consistency after delivery operations

#### 1) delivery without drone

- test added: `finishDelivery_ShouldFail_WhenNoDroneAssigned()`
- controller and service simplified to throw exception - 409 conflict

   Improvements:
    - focus
      * don't need to test of error message content _yet_
      * Focus on status codes for REST API testing - code 409 for conflict
    - readability
      * Keep exception handling simple in the controller
      * Let service layer contain business logic and throw domain-specific exceptions

#### 2) data consistency
   focus:
   - No need for database cleanup
   - Focus on verifying the controller handles data transformations correctly 
   - Ensure proper status codes and response structures 
   - Verify the contract between controller and service

-

### Log 2:
new tests for extended coverage 
- verify a delivery cannot be scheduled without a drone
- verify a delivery cannot be scheduled with a retired drone
- verify a delivery cannot be scheduled with a disabled drone
- verify a delivery cannot be scheduled with a busy drone