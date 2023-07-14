# Booking Service

## Running

### Prerequisites

- Java 17
- PostgreSQL 15
- Docker (if running Docker image)

### Steps for Docker

1. Start PostgreSQL server and create a database
2. Build docker image
    ```shell
    docker build -t cse-mosip/bookingservice .
    ```
3. Run docker image (change configuration values as necessary)
    ```shell
    docker run \
        -e SPRING_APPLICATION_JSON='{"spring": {"datasource": {"url": "jdbc:postgresql://host.docker.internal:5432/mosip_booking", "username": "mosip", "password": "mosip"}}}' \
        -p 8080:8080 \
        cse-mosip/booking-service
    ```
4. Server is now running at [http://localhost:8080](http://localhost:8080)

### Steps for running manually

1. Start PostgreSQL server and create a database
2. Create a [profile-specific configuration file](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.files.profile-specific), e.g. `src/main/resources/application-local.yaml` (modify values as necessary)
   ```yaml
   spring:
      datasource:
         url: "jdbc:postgresql://localhost:5432/mosip_booking"
         username: "mosip"
         password: "mosip"
   ```
3. Set the current profile in the `SPRING_PROFILES_ACTIVE` environment variable (run the command for your specific shell)

   Bash:
   ```shell
   export SPRING_PROFILES_ACTIVE=local
   ```
   Powershell:
   ```powershell
   $env:SPRING_PROFILES_ACTIVE = 'local'
   ```
   CMD:
   ```cmd
   set SPRING_PROFILES_ACTIVE=local
   ```
4. Run the backend
   ```shell
   ./mvnw spring-boot:run
   ```
