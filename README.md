## Test Suite for Import/Export Service

### Basic Structure

#### Client

Test Client for performing requests to service under test. Implements basic operations to interact with service based on assumed API specification.

#### Config

Configuration for test run and logging settings. Test run parameters are set in "/resources/run.properties" and set to local testing with service stub. Can be reset to actual service FQDN when implemented.

#### MapExporterService

Basic service implementation according "service.proto" assumed specification to implement and debug tests.

#### Stub

Encapsulates test server start with basic service. Implementation can be changed to actual service and stubbing service dependencies such as database when implemented.

#### TestServer

Start and shutdown test server

#### service.proto

Service API assumed description to make test samples less abstract for the task.

#### TestCases

JUnit-based test cases for the project with Allure reports.

### Run tests

**mvn clean test**

### Generate Allure report

**mvn allure:serve**

To report being generated test results must be located in "/target/allure-reports" directory.
