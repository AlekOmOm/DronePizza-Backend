

# Test Database Helper

The `TestDatabaseHelper` class is 
- a utility class that provides a simple way to manage test data in a database. 
- designed to be used in unit tests to create, update, and delete test data in a database. 
- provides builder pattern
  - allows you to easily create and manage test data in a database.

advantages:
   - Centralized deletion logic that handles dependencies correctly
   - Reusable across all test classes
   - Easy to maintain and modify if the entity relationships change
   - Clear tracking of which entities need cleanup
   - Fluent builder pattern for easy use