# Booking Service

## Running

### Pre-requisites

- Java 17
- Postgresql 15
- Docker (if running Docker image)

### Steps for Docker

1. Start postgresql server and create a database
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
