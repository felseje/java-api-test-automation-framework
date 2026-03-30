# Java API Test Automation Framework

[![Build Status](https://github.com/felseje/java-api-test-automation-framework/actions/workflows/maven-ci.yml/badge.svg)](https://github.com/felseje/java-api-test-automation-framework/actions)

## Project Description

The **Java API Test Automation Framework** is an automated testing framework for backend APIs, developed in Java. It uses **JUnit Jupiter** for testing and **RestAssured** to simplify HTTP calls and response validations. The project provides a modular structure, ready for building, organizing, and executing robust and reusable tests.

---

## Prerequisites

Before running the project, make sure you have installed:

- [Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven 3.9+](https://maven.apache.org/download.cgi)
- Your preferred IDE (IntelliJ, Eclipse, VSCode, etc.)

---

## Installation

1. Clone the repository:

```bash
git clone https://github.com/felseje/java-api-test-automation-framework.git
cd java-api-test-automation-framework
```

2. Install Maven dependencies:

```bash
mvn clean install
```

## Running Tests

To run the tests:

```bash
mvn verify
```

Test reports will be generated in:

```
target/surefire-reports
target/site/failsafe-report
```

## Project Structure

```
src/
 └── main/
     └── java/
         └── io/github/felseje/apitestautomation/
             ├── config/            # Project configuration
             ├── core/
             │    ├── client/       # Abstract class for HTTP logic
             │    ├── request/      # Assists writing tests (requests, retries)
             │    └── response/     # Response context and assertion helpers
             ├── factory/           # Request and Response Specification factories
             ├── util/              # Utility classes
             └── exception/         # Standard framework exceptions
resources/
 └── config.properties              # Default project properties file
```
## Example Tests

You can find detailed example tests in the [`example`](https://github.com/felseje/java-api-test-automation-framework/tree/example) branch.

## Technologies Used

- [Java 21](https://openjdk.org/projects/jdk/21/) – Modern, high-performance JDK
- [Maven](https://maven.apache.org/) – Build automation and dependency management
- [JUnit Jupiter 6](https://junit.org/junit5/docs/current/user-guide/) – Unit and integration testing framework
- [RestAssured 6](https://rest-assured.io/) – Simplified REST API testing
- [Awaitility 4.3](https://awaitility.github.io/awaitility/) – Handling asynchronous operations in tests
- [SLF4J](http://www.slf4j.org/) + [Logback](https://logback.qos.ch/) – Logging framework
- [Lombok](https://projectlombok.org/) – Boilerplate code reduction
- [Owner](https://github.com/lviggiano/owner) – Type-safe configuration management

## License

This project is licensed under the **MIT License**. See the [LICENSE](https://opensource.org/licenses/MIT) file for details.