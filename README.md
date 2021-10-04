NOTE:
Application starts on 8080 port.

1.To start application execute next commands in terminal:

    1. mvn clean package
    2. mvn spring-boot:run
    
Or you can run application with a jar file:

    1. mvn clean package
    2. java -jar target/country-by-phone-0.0.1-SNAPSHOT.jar 
  
2.To run unit tests execute next command in terminal:

    mvn clean test

3.To run integration tests execute next commands in terminal:

    mvn clean test-compile failsafe:integration-test